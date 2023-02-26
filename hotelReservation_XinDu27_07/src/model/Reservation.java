package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static model.Color.*;


public class Reservation implements Serializable, Comparable<Reservation> {
    private final Customer customer;
    private final IRoom room;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;

    public Reservation(Customer customer, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return this.customer + "\nReservation:\n" + this.room + "\nCheckInDate:  " +
                ANSI_CYAN + this.checkInDate.format(DateTimeFormatter.ofPattern("E dd/MM/y")) + ANSI_RESET + "\nCheckOutDate: " + ANSI_CYAN +
                this.checkOutDate.format(DateTimeFormatter.ofPattern("E dd/MM/y")) + ANSI_RESET + "\n" + ANSI_BLUE + "**********************************************" + ANSI_RESET;
    }

    @Override
    public int compareTo(Reservation r) {
        if (getCheckInDate().isBefore(r.getCheckInDate())) { //ascending order
            return -1;
        } else if (getCheckInDate().isAfter(r.getCheckInDate())) {
            return 1;
        } else {
            return 0;
        }
    }
}

