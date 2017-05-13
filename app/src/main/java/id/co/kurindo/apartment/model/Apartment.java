package id.co.kurindo.apartment.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by DwiM on 5/13/2017.
 */

public class Apartment implements Parcelable {
    private int id;
    private List<Room> rooms;
    private String name;
    private Address address;

    public Apartment(int id, String name, Address address){
        this.id = id;
        this.name = name;
        this.address = address;
    }

    protected Apartment(Parcel in) {
    }

    public static final Creator<Apartment> CREATOR = new Creator<Apartment>() {
        @Override
        public Apartment createFromParcel(Parcel in) {
            return new Apartment(in);
        }

        @Override
        public Apartment[] newArray(int size) {
            return new Apartment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String toStoreString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        return sb.toString();
    }
}
