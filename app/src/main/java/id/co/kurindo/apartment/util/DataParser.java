package id.co.kurindo.apartment.util;

import org.json.JSONException;
import org.json.JSONObject;

import id.co.kurindo.apartment.model.Address;
import id.co.kurindo.apartment.model.Apartment;
import id.co.kurindo.apartment.model.Issue;
import id.co.kurindo.apartment.model.Room;
import id.co.kurindo.apartment.model.User;

/**
 * Created by DwiM on 5/12/2017.
 */

public class DataParser {
    public User parseUser(JSONObject jObj){
        User user = null;
        try {
            int id = jObj.getInt("id");
            String name = jObj.getString("name");
            String email = jObj.getString("email");
            String mobile= jObj.getString("mobile");
            user = new User(id, name, "", email, mobile);
            user.setRole(name.toUpperCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Room parseRoom(JSONObject jObj) {
        Room room = null;
        try {
            int id = jObj.getInt("id");
            String roomNumber = jObj.getString("room");
            Apartment apartment = parseApartment(jObj.getJSONObject("apartment"));
            User owner= parseUser(jObj.getJSONObject("owner"));
            User tenant = parseUser(jObj.getJSONObject("tenant"));
            room  = new Room(id, roomNumber, owner, tenant, apartment);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return room;
    }

    public Room parseRoomA(JSONObject jObj) {
        Room room = null;
        try {
            int id = jObj.getInt("id");
            String roomNumber = jObj.getString("name");
            String tower= jObj.getString("tower");
            String type= jObj.getString("type");
            Apartment apartment = new Apartment(id, tower, new Address());
            User owner= new User();
            owner.setFirstname(type);
            owner.setLastname(tower);
            User tenant = new User();
            room  = new Room(id, roomNumber, owner, tenant, apartment);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return room;
    }

    public Apartment parseApartment(JSONObject jObj) {
        Apartment result = null;
        try {
            int id = jObj.getInt("id");
            String name = jObj.getString("name");
            Address address = parseAddress(jObj.getJSONObject("address"));
            result  = new Apartment(id, name, address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


    public Address parseAddress(JSONObject jObj) {
        Address result = null;
        try {
            int id = jObj.getInt("id");
            String alamat = jObj.getString("alamat");
            String kec = jObj.getString("kec");
            String kab = jObj.getString("kab");
            result  = new Address(id, alamat, kec, kab);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Issue parseIssue(JSONObject jObj) {
        Issue result = null;
        try {
            String id = jObj.getString("id");
            String category = jObj.getString("category");
            String status = jObj.getString("status");
            String subject = jObj.getString("subject");
            String comment = jObj.getString("comment");
            String room = jObj.getString("room");
            //Room roomObj = jObj.getString("room");
            long dateCreated = jObj.getLong("dateCreated");
            Room room1 = new Room(room, null);
            result  = new Issue(id, category, subject, comment, room1, status, dateCreated);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
