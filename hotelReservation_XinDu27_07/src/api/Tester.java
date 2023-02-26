package api;


import model.*;
import service.CustomerService;
import service.ReservationService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import static api.HotelResource.hotelResource;


public class Tester {
    public static void main(String[] args) {

          HotelResource.loadData();
//        CustomerService.getInstance().addCustomer("Jeff", "Hoff", "jeff@gm.com");
//        CustomerService.getInstance().addCustomer("Xin", "Du", "xin@gm.com");
//
//
//        IRoom room1 = new Room("101", 135.67, RoomType.SINGLE);
//        IRoom room2 = new Room("102", 345.67, RoomType.DOUBLE);
//        IRoom room3 = new Room("103", 345.67, RoomType.DOUBLE);
//        IRoom room4 = new Room("104", 135.67, RoomType.SINGLE);
//        //   IRoom room5 = new Room("105", 234.67, RoomType.SINGLE);
//        //   IRoom room6 = new Room("106", 134.56, RoomType.DOUBLE);
//
//
 //       ReservationService.getInstance().addRoom(room1);
//        ReservationService.getInstance().addRoom(room2);
//        ReservationService.getInstance().addRoom(room3);
//        ReservationService.getInstance().addRoom(room4);
////
//        //  ReservationService.getInstance().addRoom(room5);
//        //   ReservationService.getInstance().addRoom(room6);
//
//
        LocalDate checkInDate1 = LocalDate.of(2022, 11, 4);
        LocalDate checkOutDate1 = LocalDate.of(2022, 11, 7);

        LocalDate checkInDate2 = LocalDate.of(2022, 11, 5);
        LocalDate checkOutDate2 = LocalDate.of(2022, 11, 6);

        LocalDate checkInDate3 = LocalDate.of(2022, 11, 8);
        LocalDate checkOutDate3 = LocalDate.of(2022, 11, 11);

        LocalDate checkInDate4 = LocalDate.of(2022, 11, 15);
        LocalDate checkOutDate4 = LocalDate.of(2022, 11, 17);

        LocalDate checkInDate5 = LocalDate.of(2022, 11, 7);
        LocalDate checkOutDate5 = LocalDate.of(2022, 11, 11);


    //    hotelResource.bookARoom("jeff@gm.com", room1, checkInDate1, checkOutDate1); //101: 04.11-07.11
        //    hotelResource.bookARoom("xin@gm.com", room1, checkInDate2, checkOutDate2);  //101: 05.11-06.11 already reserved


//        Reservation r1 = hotelResource.bookARoom("jeff@gm.com", room2, checkInDate2, checkOutDate2); //102: 05.11-06.11
//        Reservation r2 = hotelResource.bookARoom("xin@gm.com", room1, checkInDate5, checkOutDate5);  //101: 07.11-11.11
//        hotelResource.bookARoom("xin@gm.com", room3, checkInDate4, checkOutDate4);  //103: 15.11-17.11
//        hotelResource.bookARoom("jeff@gm.com", room4, checkInDate3, checkOutDate3); //104: 08.11-11.11


        //   hotelResource.removeCustomersReservations("jeff@gm.com");
        //   hotelResource.cancelAnAccount("jeff@gm.com");

        //   hotelResource.cancelAReservation("jeff@gm.com", hotelResource.getRoom("104"), checkInDate3, checkOutDate3);

        LocalDate in = LocalDate.of(2022, 11, 4);
        LocalDate out = LocalDate.of(2022, 11, 7);


//        for (IRoom room : hotelResource.findARoom(in, out)) {
//            System.out.println(room);
//        }
//        int range = 3;
//        Collection<IRoom> availableRooms = hotelResource.findARoom(in, out);
//        if (availableRooms.isEmpty()) {
//            System.out.println("All rooms are already booked, our recommended rooms: between " +
//                    in.plusDays(range) + " and " + out.plusDays(range));
//            hotelResource.findARoom(in.plusDays(range), out.plusDays(range));
//        }
//
//        for (IRoom room : hotelResource.findARoom(in.plusDays(range), out.plusDays(range))) {
//            System.out.println(room);
//        }


//
//        for (IRoom room : hotelResource.findARoom(in, out)) {
//            System.out.println(room);
//        }
//
//        while (hotelResource.findARoom(in, out).isEmpty()) {
//            long range = 3;
//            System.out.println("All rooms are already booked, our recommended rooms: between " +
//                    in.plusDays(range) + " and " + out.plusDays(range));
//            if (!(hotelResource.findARoom(in.plusDays(range), out.plusDays(range)).isEmpty())) {
//                for (IRoom room : hotelResource.findARoom(in.plusDays(range), out.plusDays(range))) {
//                    System.out.println(room);
//                }
//                break;
//            }
//        }

//        in = LocalDate.of(2022, 11, 4);
//        out = LocalDate.of(2022, 11, 17);
//
//
//        System.out.println(hotelResource.bookARoom("xin@gm.com", room2, in, out));



        //   System.out.println(CustomerService.getInstance().getAllCustomers());
          ReservationService.getInstance().printAllReservation();  // AllReservation
         //  HotelResource.storeData();

    }
}
