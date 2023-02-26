package api;

import model.IRoom;
import model.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Scanner;


import static api.HotelResource.hotelResource;

public class MainMenu {
    private static final String adminPassword = "Password";

    public static void mainMenu() {

        boolean keepRunning = true;
        try (Scanner scanner = new Scanner(System.in)) {
            mainMenu:
            while (keepRunning) {
                System.out.println("Welcome to the Hotel Reservation Application");
                System.out.println("**********************************************");
                System.out.println("1. Find and reserve a room");
                System.out.println("2. See my reservations");
                System.out.println("3. Create an Account");
                System.out.println("4. Admin");
                System.out.println("5. Cancel a Reservation");
                System.out.println("6. Cancel an Account");
                System.out.println("7. Exit");
                System.out.println("**********************************************");
                System.out.println("Please select a number for the menu option");

                String input = scanner.nextLine();
                try {
                    switch (input) {
                        case "1": {
                            System.out.println("Do you have an account? y/n");
                            input = scanner.next();
                            while (true) {
                                if (input.equalsIgnoreCase("y")) {
                                    break;
                                } else if (input.equalsIgnoreCase("N")) {
                                    continue mainMenu;
                                } else {
                                    System.out.println("Please enter Y (Yes) or N (No)");
                                    input = scanner.next();
                                }
                            }
                            System.out.println("Please enter Email format: name@domain.com");
                            String email = scanner.next();
                            while (!email.matches("^(.+)@(.+).(.+)$")) {
                                System.out.println("Please enter Email format: name@domain.com");
                                email = scanner.next();
                            }

                            if (hotelResource.getCustomer(email) == null) { //check account
                                System.out.println("This account does not exist.");
                                continue;
                            }

                            System.out.println("Please choose your CheckInDate" + " : dd/MM/yyyy  sample: 23/11/2004");
                            String date = scanner.next();

                            while (!date.matches("^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$")) {
                                System.out.println("Please enter checkInDate:");
                                date = scanner.next();
                            }
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate checkInDate = LocalDate.parse(date, formatter);
                            System.out.println("Please choose your CheckOutDate" + " : dd/MM/yyyy  sample: 23/11/2004");
                            date = scanner.next();

                            while (!date.matches("^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$")) {
                                System.out.println("Please enter checkOutDate:");
                                date = scanner.next();
                            }
                            LocalDate checkOutDate = LocalDate.parse(date, formatter);

                            //find available rooms

                            for (IRoom room : hotelResource.findARoom(checkInDate, checkOutDate)) {
                                System.out.println(room);
                            }

                            if (hotelResource.findARoom(checkInDate, checkOutDate).isEmpty()) {
                                long range = 3;
                                while (true) {
                                    checkInDate = checkInDate.plusDays(range);
                                    checkOutDate = checkOutDate.plusDays(range);

                                    if (!hotelResource.findARoom(checkInDate, checkOutDate).isEmpty()) {
                                        for (IRoom room : hotelResource.findARoom(checkInDate, checkOutDate)) {
                                            System.out.println(room);
                                        }
                                        System.out.println("All rooms are already booked, our recommended rooms: between " +
                                                checkInDate + " and " + checkOutDate);
                                        break;
                                    }
                                }
                            }

                            System.out.println("Please choose your RoomNumber");
                            String roomNumber = scanner.next();
                            IRoom room = hotelResource.getRoom(roomNumber);
                            System.out.println("Would you like book a room? y/n");

                            input = scanner.next();
                            while (true) {
                                if (input.equalsIgnoreCase("y")) {
                                    Reservation newReservation = hotelResource.bookARoom(email, room, checkInDate, checkOutDate);
                                    System.out.println(newReservation);
                                    break;

                                } else if (input.equalsIgnoreCase("N")) {
                                    break;
                                } else {
                                    System.out.println("Please enter Y (Yes) or N (No)");
                                    input = scanner.next();
                                }
                            }
                            continue;
                        }
                        case "2": {
                            System.out.println("Do you have an account? y/n");
                            input = scanner.next();
                            while (true) {
                                if (input.equalsIgnoreCase("y")) {
                                    break;
                                } else if (input.equalsIgnoreCase("N")) {
                                    continue mainMenu;
                                } else {
                                    System.out.println("Please enter Y (Yes) or N (No)");
                                    input = scanner.next();
                                }
                            }
                            System.out.println("Please enter Email format: name@domain.com");
                            String email = scanner.next();
                            while (!email.matches("^(.+)@(.+).(.+)$")) {
                                System.out.println("Please enter Email format: name@domain.com");
                                email = scanner.next();
                            }

                            if (hotelResource.getCustomer(email) == null) { //check account
                                System.out.println("This account does not exist.");
                                continue;
                            }

                            for (Reservation customerReservation : hotelResource.getCustomersReservations(email)) {
                                System.out.println(customerReservation);
                            }
                            continue;
                        }
                        case "3": {
                            System.out.println("Please enter Email format: name@domain.com");
                            String email = scanner.next();
                            while (!email.matches("^(.+)@(.+).(.+)$")) {
                                System.out.println("Please enter Email format: name@domain.com");
                                email = scanner.next();
                            }
                            if (hotelResource.getCustomer(email) != null) { //check account
                                System.out.println("This account already exists.");
                                continue;
                            }
                            System.out.println("Please enter your FirstName");
                            String firstName = scanner.next();
                            System.out.println("Please enter your LastName");
                            String lastName = scanner.next();
                            hotelResource.createACustomer(email, firstName, lastName);
                            continue;
                        }
                        case "4": {
                            System.out.println("Please enter your password (Password)");
                            input = scanner.next();
                            while (true) {
                                if (input.equals(adminPassword)) {
                                    break;
                                } else {
                                    continue mainMenu;
                                }
                            }
                            System.out.println("Welcome to Admin Menu");
                            AdminMenu.adminMenu();    //switch to AdminMenu in AdminResource
                            keepRunning = false;
                            break;
                        }
                        case "5": {
                            System.out.println("Do you have an account? y/n");
                            input = scanner.next();
                            while (true) {
                                if (input.equalsIgnoreCase("y")) {
                                    break;
                                } else if (input.equalsIgnoreCase("N")) {
                                    continue mainMenu;
                                } else {
                                    System.out.println("Please enter Y (Yes) or N (No)");
                                    input = scanner.next();
                                }
                            }
                            System.out.println("Please enter Email format: name@domain.com");
                            String email = scanner.next();
                            while (!email.matches("^(.+)@(.+).(.+)$")) {
                                System.out.println("Please enter Email format: name@domain.com");
                                email = scanner.next();
                            }

                            if (hotelResource.getCustomer(email) == null) { //check account
                                System.out.println("This account does not exist.");
                                continue;
                            }
                            for (Reservation reservation : hotelResource.getCustomersReservations(email)) {
                                System.out.println(reservation);
                            }
                            System.out.println("which booking do you want to cancel?");
                            System.out.println("Please enter your CheckInDate" + " : dd/MM/yyyy");
                            String date = scanner.next();

                            while (!date.matches("^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$")) {
                                System.out.println("Please enter checkInDate:");
                                date = scanner.next();
                            }
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate checkInDate = LocalDate.parse(date, formatter);
                            System.out.println("Please choose your CheckOutDate" + " : dd/MM/yyyy");
                            date = scanner.next();

                            while (!date.matches("^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$")) {
                                System.out.println("Please enter checkOutDate:");
                                date = scanner.next();
                            }
                            LocalDate checkOutDate = LocalDate.parse(date, formatter);
                            System.out.println("Please choose your RoomNumber");
                            String roomNumber = scanner.next();
                            IRoom room = hotelResource.getRoom(roomNumber);
                            System.out.println("Do you want to cancel that room? y/n");

                            input = scanner.next();
                            while (true) {
                                if (input.equalsIgnoreCase("y")) {
                                    hotelResource.cancelAReservation(email, room, checkInDate, checkOutDate);
                                    break;

                                } else if (input.equalsIgnoreCase("N")) {
                                    break;
                                } else {
                                    System.out.println("Please enter Y (Yes) or N (No)");
                                    input = scanner.next();
                                }
                            }
                            continue;
                        }
                        case "6": {
                            System.out.println("Please enter Email format: name@domain.com");
                            String email = scanner.next();
                            while (!email.matches("^(.+)@(.+).(.+)$")) {
                                System.out.println("Please enter Email format:");
                                email = scanner.next();
                            }
                            if (hotelResource.getCustomer(email) == null) { //check account
                                System.out.println("This account does not exist.");
                                continue;
                            }
                            System.out.println("Are you sure you want to cancel this account y/n");
                            input = scanner.next();

                            while (true) {
                                if (input.equalsIgnoreCase("y")) {
                                    hotelResource.cancelAnAccount(email);
                                    hotelResource.removeCustomersReservations(email);
                                    continue mainMenu;
                                } else if (input.equalsIgnoreCase("N")) {
                                    break;                        // exit addARoomMenu, back to AdminMenu
                                } else {
                                    System.out.println("Please enter Y (Yes) or N (No)");
                                    input = scanner.next();
                                }
                            }

                        }
                        case "7": {
                            keepRunning = false;
                            break;
                        }
                    }
                } catch (NullPointerException ex) {
                    ex.getLocalizedMessage();
                }
            }
        } catch (NoSuchElementException ex) {
            System.out.println("Invalid Input");
        } catch (IllegalArgumentException ex) {
            ex.getLocalizedMessage();
        }
    }
}
