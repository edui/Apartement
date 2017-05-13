package id.co.kurindo.apartment.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aspire on 12/23/2016.
 */

public class Address implements Parcelable {
    private int id;
    private String alamat;
    private String kec;
    private String kab;
    private City city;

    public Address(){}
    public Address(int id, String alamat, String kec, String kab){
        this.id = id;
        this.alamat = alamat;
        this.kec = kec;
        this.kab = kab;
    }

    protected Address(Parcel in) {
        id = in.readInt();
        alamat = in.readString();
        kec = in.readString();
        kab = in.readString();
        city = in.readParcelable(City.class.getClassLoader());
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(alamat);
        dest.writeString(kec);
        dest.writeString(kab);
        dest.writeParcelable(city, flags);

    }
}
