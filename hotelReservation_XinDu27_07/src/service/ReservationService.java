package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;


/**
 *
 */


public class ReservationService {
    private static ReservationService reservationService;
    private final Set<IRoom> allRooms;
    private final Set<Reservation> reservations;
    private final Set<IRoom> reservationsRooms;

    private ReservationService() {
        allRooms = new LinkedHashSet<>();
        reservations = new TreeSet<>();
        reservationsRooms = new LinkedHashSet<>();
    }

    public static ReservationService getInstance() { //static reference
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    Set<Reservation> getReservations() {
        return reservations;
    }

    public Collection<IRoom> getAllRooms() {
        return allRooms;
    }

    public void addRoom(IRoom room) {
        if (allRooms.isEmpty()) {
            allRooms.add(room);
        } else {
            for (IRoom Iroom : allRooms) {
                if (Iroom.getRoomNumber().equals(room.getRoomNumber())) { // check roomNumber
                    if (Iroom.getRoomPrice() == 0.0) { //admin can set the price of freeRoom
                        allRooms.remove(Iroom);
                        allRooms.add(room);
                    } else {
                        System.out.println("Room " + Iroom.getRoomNumber() + " already exists");
                    }
                    return;
                }
            }
            allRooms.add(room);
        }
    }

    public IRoom getRoom(String roomNumber) {
        for (IRoom room : allRooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        System.out.println("This is room does not exist");
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {//new customer
        if (checkInDate.isBefore(LocalDate.now()) || checkOutDate.isBefore(checkInDate)) {
            System.out.println("Invalid Date");
            return null;
        } else {
            for (Reservation reservation : reservations) {
                //check room  first check roomNumber
                if (reservation.getRoom().getRoomNumber().equals(room.getRoomNumber())) {
                    if (reservation.getCheckInDate().isBefore(checkInDate) && reservation.getCheckOutDate().isAfter(checkOutDate) || reservation.getCheckInDate().equals(checkInDate)) { //check Date  [checkIn, checkOut) customer can check in on the day of checkout
                        System.out.println("Room " + reservation.getRoom().getRoomNumber() + " between " + checkInDate + " and " + checkOutDate + " is already reserved.");
                        return null;
                    }
                }
            }
        }

        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(newReservation); //add reservation to reservations
        reservationsRooms.add(room);      //add room to reservationsRooms
        System.out.println(reservationsRooms);
        return newReservation;
    }

    public Collection<IRoom> findRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        Set<IRoom> availableRooms = new LinkedHashSet<>(reservationsRooms); //copy all reservationsRooms
        if (checkOutDate.isBefore(checkInDate)) { //check dates
            System.out.println("Invalid checkOutDate");
        } else {
            for (Reservation reservation : reservations) {
                LocalDate in = reservation.getCheckInDate();
                LocalDate out = reservation.getCheckOutDate();

                //first check overlapping dates
                if (checkOutDate.isBefore(in) || checkInDate.isAfter(out) || checkOutDate.isEqual(in) || checkInDate.equals(out)) {
                    // can be checked in on the same day as checkout
                    // Here we don't need to do anything, because we have already copied all reservationsRooms
                    //we only need to remove room with overlapping dates
                    // System.out.println("fff");

                } else {
                    availableRooms.remove(reservation.getRoom());
                    //  System.out.println(availableRooms);
                    // availableRooms may contain room with overlapping dates
                    // because the same room corresponds to different dates
                    // must remove room with overlapping dates from availableRooms
                }
            }

            for (IRoom room : allRooms) {
                if (!reservationsRooms.contains(room)) { // add all rooms that have not been reserved to availableRooms
                    availableRooms.add(room);
                }
            }
        }
        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        List<Reservation> customersReservations = new LinkedList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().getEmail().equals(customerEmail)) {
                customersReservations.add(reservation);
            }
        }
        return customersReservations;
    }

    public void printAllReservation() {
        for (Reservation reservation : getReservations()) {
            System.out.println(reservation);
        }
    }

    public void removeCustomersReservations(String customerEmail) {
        reservations.removeIf((reservation) -> reservation.getCustomer().getEmail().equals(customerEmail));
    }

    public void cancelARoom(String customerEmail, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkOutDate.isBefore(checkInDate)) {
            System.out.println("Invalid checkOutDate");
        } else {
            reservations.removeIf((reservation) -> reservation.getCustomer().getEmail().equals(customerEmail) && reservation.getRoom().equals(room) && reservation.getCheckInDate().equals(checkInDate) && reservation.getCheckOutDate().equals(checkOutDate));
        }
    }

    public void writeData() {
        //store all data into files

        Path customers = FileSystems.getDefault().getPath("customers.dat");

        try (ObjectOutputStream locFile = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(customers)))) {
            for (Customer customer : CustomerService.getInstance().customerSet) {
                locFile.writeObject(customer);
            }

        } catch (InvalidClassException e) {
            System.out.println("InvalidClassException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }


        Path allReservations = FileSystems.getDefault().getPath("reservations.dat");
        try (ObjectOutputStream locFile = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(allReservations)))) {
            for (Reservation reservation : reservations) {
                locFile.writeObject(reservation);
            }
        } catch (InvalidClassException e) {
            System.out.println("InvalidClassException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }

        Path rooms = FileSystems.getDefault().getPath("allRooms.dat");
        try (ObjectOutputStream locFile = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(rooms)))) {
            for (IRoom room : allRooms) {
                locFile.writeObject(room);
            }
        } catch (InvalidClassException e) {
            System.out.println("InvalidClassException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }

        Path reservedRooms = FileSystems.getDefault().getPath("reservationsRooms.dat");
        try (ObjectOutputStream locFile = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(reservedRooms)))) {
            for (IRoom reservedRoom : reservationsRooms) {
                locFile.writeObject(reservedRoom);
            }
        } catch (InvalidClassException e) {
            System.out.println("InvalidClassException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }

    }

    public void readData() {
        Path customers = FileSystems.getDefault().getPath("customers.dat");
        try (ObjectInputStream locFile = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(customers)))) {
            boolean eof = false;
            while (!eof) {
                try {
                    Customer customer = (Customer) locFile.readObject();
                    CustomerService.getInstance().customerSet.add(customer);
                } catch (EOFException e) {
                    eof = true;
                }
            }
        } catch (InvalidClassException e) {
            System.out.println("InvalidClassException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException " + e.getMessage());
        }

        Path allReservations = FileSystems.getDefault().getPath("reservations.dat");
        try (ObjectInputStream locFile = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(allReservations)))) {
            boolean eof = false;
            while (!eof) {
                try {
                    Reservation reservation = (Reservation) locFile.readObject();
                    reservations.add(reservation);
                } catch (EOFException e) {
                    eof = true;
                }
            }
        } catch (InvalidClassException e) {
            System.out.println("InvalidClassException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException " + e.getMessage());
        }

        Path rooms = FileSystems.getDefault().getPath("allRooms.dat");
        try (ObjectInputStream locFile = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(rooms)))) {
            boolean eof = false;
            while (!eof) {
                try {
                    IRoom room = (IRoom) locFile.readObject();
                    allRooms.add(room);
                } catch (EOFException e) {
                    eof = true;
                }
            }
        } catch (InvalidClassException e) {
            System.out.println("InvalidClassException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException " + e.getMessage());
        }


        Path reservedRooms = FileSystems.getDefault().getPath("reservationsRooms.dat");
        try (ObjectInputStream locFile = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(reservedRooms)))) {
            boolean eof = false;
            while (!eof) {
                try {
                    IRoom reservedRoom = (IRoom) locFile.readObject();
                    reservationsRooms.add(reservedRoom);
                } catch (EOFException e) {
                    eof = true;
                }
            }
        } catch (InvalidClassException e) {
            System.out.println("InvalidClassException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException " + e.getMessage());
        }

    }

}


