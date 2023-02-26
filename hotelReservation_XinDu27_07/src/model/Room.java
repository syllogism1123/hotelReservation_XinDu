package model;

public class Room implements IRoom {
    private final String roomNumber;
    private final Double price;
    private final RoomType roomType;

    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }


    @Override
    public boolean equals(Object obj) { // override equals() define Equality
        if (obj == null) {
            return false;
        }

        if (obj instanceof Room room) {
            return roomNumber.equals(room.getRoomNumber()) &&
                    price.equals(room.getRoomPrice()) &&
                    roomType.equals(room.getRoomType());
        }
        return false;

    }

    @Override
    public int hashCode() { // override hashCode() define Equality
        return this.roomNumber.hashCode();
    }

    @Override
    public String toString() {
        return "RoomNumber:   " + this.getRoomNumber() + " Price: $" + String.format("%6.2f", this.price) + "/night " +
                " RoomType: " + this.getRoomType();
    }
}
