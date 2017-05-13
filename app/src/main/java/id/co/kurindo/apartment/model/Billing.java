package id.co.kurindo.apartment.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import id.co.kurindo.apartment.util.DummyData;

/**
 * Created by dwim on 1/8/2017.
 */

public class Billing implements Parcelable {
    private String month;
    private String year;
    private List<Issue> issues;
    private double totalCost;
    private String paymentDate;
    private String paidDate;
    public Billing(String year, String month){
        this.year = year;
        this.month = month;
    }
    public Billing(String year, String month, List producst, String paymentDate, String paidDate){
        this.year = year;
        this.month = month;
        this.issues = producst;
        this.paymentDate = paymentDate;
        this.paidDate = paidDate;
    }
    protected Billing(Parcel in) {
        month = in.readString();
        year = in.readString();
        issues = in.createTypedArrayList(Issue.CREATOR);
        totalCost = in.readDouble();
        paymentDate = in.readString();
        paidDate = in.readString();
    }

    public static final Creator<Billing> CREATOR = new Creator<Billing>() {
        @Override
        public Billing createFromParcel(Parcel in) {
            return new Billing(in);
        }

        @Override
        public Billing[] newArray(int size) {
            return new Billing[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(year);
        dest.writeString(month);
        dest.writeDouble(totalCost);
        dest.writeString(paymentDate);
        dest.writeTypedList(issues);
        dest.writeString(paidDate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //sb.append(month+" "+year+"\n");
        if(issues != null){
            double totalCost = 0;
            for (int i = 0; i < issues.size(); i++) {
                Issue p = issues.get(i);
                totalCost += p.getCost();
                sb.append(p.toStringSummary()+"\n\n");
            }
            sb.append("\nTotal Cost : "+ DummyData.formatCurrency(totalCost));
            sb.append("\nPayment Date : "+paymentDate);
            sb.append("\nActual Paid  : "+paidDate);
        }
        return sb.toString();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }
}
