package id.co.kurindo.apartment.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aspire on 12/23/2016.
 */

public class User extends Person {
    private String token;
    private String uid;
    private String role;
    private String city;
    public User(){
        super();
    }
    public User(int id, String firstname, String lastname, String email, String phone){
        super(id, firstname, lastname, email, phone);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    protected User(Parcel in) {
        super(in);
    }
}
