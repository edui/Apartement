package id.co.kurindo.apartment.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import id.co.kurindo.apartment.util.DummyData;

/**
 * Created by aspire on 12/29/2016.
 */

public class Product implements Parcelable {
    private String name;
    private int drawable;
    private String comments;
    private List<Pilihan> items = new ArrayList<>();
    private double cost;

    public Product(int drawable, String name) {
        this.drawable = drawable;
        this.name = name;
    }

    protected Product(Parcel in) {
        name = in.readString();
        drawable = in.readInt();
        comments = in.readString();
        cost = in.readDouble();
        items = in.createTypedArrayList(Pilihan.CREATOR);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
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
        dest.writeString(comments);
        dest.writeDouble(cost);
        dest.writeTypedList(items);
    }

    public String toStringSummary() {
        StringBuilder sb =new StringBuilder();
        sb.append(getName()+" : "+ DummyData.formatCurrency( getCost()));
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
}
