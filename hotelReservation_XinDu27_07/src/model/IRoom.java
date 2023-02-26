package model;

import java.io.Serializable;

public interface IRoom extends Serializable {
    String getRoomNumber(); //public abstract

    Double getRoomPrice(); // public abstract

    RoomType getRoomType(); //public abstract

    boolean isFree();      //public abstract
}
