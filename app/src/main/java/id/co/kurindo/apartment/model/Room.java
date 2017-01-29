package id.co.kurindo.apartment.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dwim on 1/28/2017.
 */

public class Room implements Parcelable {
    private String roomNumber;
    private User owner;
    private String renter;

    public Room(String roomNumber, User owner){
        this.roomNumber = roomNumber;
        this.owner = owner;
    }

    protected Room(Parcel in) {
        roomNumber = in.readString();
        renter = in.readString();
        owner = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(roomNumber);
        dest.writeString(renter);
        dest.writeParcelable(owner, flags);
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getRenter() {
        return renter;
    }

    public void setRenter(String renter) {
        this.renter = renter;
    }
}
