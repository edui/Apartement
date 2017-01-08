package id.co.kurindo.apartment.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aspire on 12/23/2016.
 */

public class City implements Parcelable {
    private String code;
    private String text;

    protected City(Parcel in) {
        code = in.readString();
        text = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(text);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
