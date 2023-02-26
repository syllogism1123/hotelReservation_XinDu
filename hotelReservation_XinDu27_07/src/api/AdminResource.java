package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.*;


class AdminResource {
    public static final AdminResource adminResource = new AdminResource();  //static reference

    Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }

    void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            ReservationService.getInstance().addRoom(room);
        }
    }

    Collection<IRoom> getAllRooms() {
        return ReservationService.getInstance().getAllRooms();
    }

    Collection<Customer> getAllCustomers() {
        return CustomerService.getInstance().getAllCustomers();
    }

    void displayAllReservations() {
        ReservationService.getInstance().printAllReservation();
    }

}
