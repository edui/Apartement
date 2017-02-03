package id.co.kurindo.apartment.model;

import android.os.Parcel;
import android.os.Parcelable;

import id.co.kurindo.apartment.util.DummyData;

/**
 * Created by dwim on 1/8/2017.
 */

public class History implements Parcelable {
    private String date;
    private String report;
    private String pic;
    private String status;

    public History(String date, String report){
        this(date, report , DummyData.STATUS_NEW);
    }
    public History(String date, String report, String status){
        this.date = date;
        this.report = report;
        this.status = status;
    }
    protected History(Parcel in) {
        date = in.readString();
        report = in.readString();
        pic = in.readString();
        status = in.readString();
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(report);
        dest.writeString(pic);
        dest.writeString(status);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
