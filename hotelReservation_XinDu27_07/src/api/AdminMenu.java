package api;

import model.*;

import java.util.*;

import static api.AdminResource.adminResource;


class AdminMenu {

    static void adminMenu() {

        try (Scanner scanner = new Scanner(System.in)) {  //try with resource
            boolean keepRunning = true;
            while (keepRunning) {
                System.out.println("**********************************************");
                System.out.println("1. See all Customers");
                System.out.println("2. See all Rooms");
                System.out.println("3. See all Reservation");
                System.out.println("4. Add a Room");
                System.out.println("5. Find a Customer");
                System.out.println("6. Back to Main Menu");
                System.out.println("**********************************************");
                System.out.println("Please select a number for the menu option");

                String input = scanner.next();
                switch (input) {
                    case "1": {
                        System.out.println("CustomerList:");
                        for (Customer customer : adminResource.getAllCustomers())
                            System.out.println(customer);
                        continue;
                    }
                    case "2": {
                        for (IRoom room : adminResource.getAllRooms()) {
                            System.out.println(room);
                        }
                        continue;
                    }
                    case "3": {
                        adminResource.displayAllReservations();
                        continue;
                    }
                    case "4": {
                        addARoomMenu();
                        break;
                    }
                    case "5": {
                        System.out.println("Please enter Email format: name@domain.com");
                        String email = scanner.next();
                        while (!email.matches("^(.+)@(.+).(.+)$")) {
                            System.out.println("Please enter Email format:");
                            email = scanner.next();
                        }
                        if (adminResource.getCustomer(email) == null) { //check account
                            System.out.println("This account does not exist.");
                            continue;
                        }
                        System.out.println(adminResource.getCustomer(email));
                        continue;
                    }
                    case "6": {
                        MainMenu.mainMenu();
                        keepRunning = false;
                        break;
                    }
                }
            }
        } catch (NoSuchElementException ex) {
            ex.getLocalizedMessage();
        }
    }

    private static void addARoomMenu() {
        Scanner scanner = new Scanner(System.in);

        addARoomMenu:
        //label for loopControl
        try {
            while (true) {
                System.out.println("Enter room number");
                String roomNumber = scanner.next();
                while (!roomNumber.matches("^\\d{3}[A-Za-z]?$")) {
                    System.out.println("Enter room number sample: 101 / 204A");
                    roomNumber = scanner.next();
                }

                System.out.println("Enter price per night");
                double price = scanner.nextDouble();

                System.out.println("Enter room type: 1 for Single bed, 2 for Double bed");
                String input = scanner.next();
                RoomType roomType;
                while (true) {
                    if (input.equals("1")) {
                        System.out.println("Single bed");
                        roomType = RoomType.SINGLE;
                        break;
                    } else if (input.equals("2")) {
                        System.out.println("Double bed");
                        roomType = RoomType.DOUBLE;
                        break;
                    } else {
                        System.out.println("Enter room type: 1 for Single bed, 2 for Double bed");
                        input = scanner.next();
                    }
                }

                List<IRoom> rooms = new LinkedList<>();
                rooms.add(new Room(roomNumber, price, roomType));
                adminResource.addRoom(rooms);     // add room

                System.out.println("Would you like to add another room y/n");
                input = scanner.next();

                while (true) {
                    if (input.equalsIgnoreCase("y")) {
                        break;
                    } else if (input.equalsIgnoreCase("N")) {
                        break addARoomMenu;                        // exit addARoomMenu, back to AdminMenu
                    } else {
                        System.out.println("Please enter Y (Yes) or N (No)");
                        input = scanner.next();
                    }
                }
            }
        } catch (InputMismatchException ex) {
            System.out.println("Invalid Input " + ex.getLocalizedMessage());
        } catch (NoSuchElementException ex) {
            ex.getLocalizedMessage();
        }
    }

}
