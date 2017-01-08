package id.co.kurindo.apartment.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aspire on 12/29/2016.
 */

public class Pilihan implements Parcelable {
    private String code;
    private String text;

    public Pilihan(String code, String text){
        this.code= code;
        this.text = text;
    }

    protected Pilihan(Parcel in) {
        code = in.readString();
        text = in.readString();
    }

    public static final Creator<Pilihan> CREATOR = new Creator<Pilihan>() {
        @Override
        public Pilihan createFromParcel(Parcel in) {
            return new Pilihan(in);
        }

        @Override
        public Pilihan[] newArray(int size) {
            return new Pilihan[size];
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

