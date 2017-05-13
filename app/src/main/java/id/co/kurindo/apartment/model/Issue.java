package id.co.kurindo.apartment.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.co.kurindo.apartment.base.AppConfig;
import id.co.kurindo.apartment.util.DummyData;

/**
 * Created by aspire on 12/29/2016.
 */

public class Issue implements Parcelable {
    private String id;
    private String name;
    private int drawable;
    private List<Pilihan> items = new ArrayList<>();
    private double cost;
    private String category;
    private String status = AppConfig.STATUS_REVIEW;
    private String subject;
    private String comment;
    private Room room;
    private long created;
    private long solved;
    private String pic;

    public Issue(int drawable, String name) {
        this.drawable = drawable;
        this.name = name;
    }
    public Issue(String id, String category, String subject, String comment, Room room, String status, long dateCreated) {
        this.id = id;
        this.category = category;
        this.subject = subject;
        this.comment =comment;
        this.room = room;
        this.status = status;
        this.created = dateCreated;
    }

    protected Issue(Parcel in) {
        name = in.readString();
        drawable = in.readInt();
        comment = in.readString();
        cost = in.readDouble();
        //items = in.createTypedArrayList(Pilihan.CREATOR);
    }

    public static final Creator<Issue> CREATOR = new Creator<Issue>() {
        @Override
        public Issue createFromParcel(Parcel in) {
            return new Issue(in);
        }

        @Override
        public Issue[] newArray(int size) {
            return new Issue[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(drawable);
        dest.writeString(comment);
        dest.writeDouble(cost);
        //dest.writeTypedList(items);
    }

    public String toStringSummary() {
        StringBuilder sb =new StringBuilder();
        if(getName() !=null ) sb.append(getName());
        if(getCategory() !=null ) sb.append("\nJenis Aduan :"+getCategory());
        if(getSubject() !=null ) sb.append("\nJudul :"+getSubject());
        if(getComment() !=null ) sb.append("\nKomentar :"+getComment());
        sb.append("\nWaktu Laporan :"+getReportDate());
        sb.append("\nStatus :"+getStatus());
        if(getSolved() > 0) sb.append("\nWaktu :"+getSolvedDate());
        if(getPic() != null) sb.append("\nPIC :"+getPic());
        if(getCost() > 0) sb.append("\nCost : "+ DummyData.formatCurrency( getCost()));
        return sb.toString();
    }

    public String getReportDate() {
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
        return sdf.format(new Date(getCreated()));
    }

    public String getSolvedDate() {
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
        return sdf.format(new Date(getSolved()));
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comments) {
        this.comment = comments;
    }

    public List<Pilihan> getItems() {
        return items;
    }

    public void setItems(List<Pilihan> items) {
        this.items = items;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getCreated() {
        return created;
    }

    public long getSolved() {
        return solved;
    }

    public void setSolved(long solved) {
        this.solved = solved;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

