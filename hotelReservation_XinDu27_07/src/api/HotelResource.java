package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;


import java.time.LocalDate;
import java.util.Collection;


public class HotelResource {
    static final HotelResource hotelResource = new HotelResource();

    Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }

    void createACustomer(String email, String firstName, String lastName) {
        CustomerService.getInstance().addCustomer(firstName, lastName, email);
    }

    IRoom getRoom(String roomNumber) {
        return ReservationService.getInstance().getRoom(roomNumber);
    }

    Reservation bookARoom(String customerEmail, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {//account exists
        Customer customer = CustomerService.getInstance().getCustomer(customerEmail);
        if (customer != null) {
            return ReservationService.getInstance().reserveARoom(customer, room, checkInDate, checkOutDate);
        } else {
            System.out.println("This account does not exist");
        }
        return null;
    }

    Collection<Reservation> getCustomersReservations(String customerEmail) {
        return ReservationService.getInstance().getCustomersReservations(customerEmail);
    }

    Collection<IRoom> findARoom(LocalDate checkInDate, LocalDate checkOutDate) {
        return ReservationService.getInstance().findRooms(checkInDate, checkOutDate);
    }

    void cancelAnAccount(String email) {
        CustomerService.getInstance().deleteCustomer(email);
    }

    void removeCustomersReservations(String customerEmail) {
        ReservationService.getInstance().removeCustomersReservations(customerEmail);
    }

    void cancelAReservation(String customerEmail, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        ReservationService.getInstance().cancelARoom(customerEmail, room, checkInDate, checkOutDate);
    }

    public static void storeData() {
        ReservationService.getInstance().writeData();
    }

    public static void loadData() {
        ReservationService.getInstance().readData();
    }

}



























