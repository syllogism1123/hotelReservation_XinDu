package model;

public class FreeRoom extends Room { // no idea about what FreeRoom means here
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0.0, roomType);
    }

    @Override
    public boolean isFree() {
        return true;
    }
}
