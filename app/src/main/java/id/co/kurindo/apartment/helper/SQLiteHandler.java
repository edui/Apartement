package id.co.kurindo.apartment.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import id.co.kurindo.apartment.model.User;


/**
 * Created by DwiM on 11/8/2016.
 */
public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "kurindoapp_api";

    // Login table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_RECIPIENT = "recipient";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_UID = "uid";
    private static final String KEY_ROLE = "role";
    private static final String KEY_CITY = "city";
    private static final String KEY_CITYTEXT = "cityText";
    private static final String KEY_ACTIVE = "active";
    private static final String KEY_APPROVED = "approved";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_API = "api_key";

    private static final String KEY_NAME = "name";
    private static final String KEY_ALAMAT = "alamat";
    private static final String KEY_RT = "rt";
    private static final String KEY_RW = "rw";
    private static final String KEY_DUSUN = "dusun";
    private static final String KEY_DESA = "desa";
    private static final String KEY_KECAMATAN = "kecamatan";
    private static final String KEY_KABUPATEN = "kabupaten";
    private static final String KEY_PROPINSI = "propinsi";
    private static final String KEY_NEGARA = "negara";
    private static final String KEY_KODEPOS = "kodepos";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FIRSTNAME + " TEXT,"+ KEY_LASTNAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_PHONE + " TEXT ," + KEY_UID + " TEXT,"
                + KEY_ROLE + " TEXT," + KEY_CITY + " TEXT,"
                + KEY_API + " TEXT,"
                + KEY_ACTIVE+ " BOOLEAN," + KEY_APPROVED + " BOOLEAN,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String firstname, String lastname, String email, String phone, String uid, String role, String city, String api, boolean active, boolean approved, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, firstname); // Name
        values.put(KEY_LASTNAME, lastname); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_PHONE, phone);
        values.put(KEY_ROLE, role);
        values.put(KEY_CITY, city);
        values.put(KEY_API, api);
        values.put(KEY_ACTIVE, (active ? 1:0));
        values.put(KEY_APPROVED, (approved ? 1:0));
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put(KEY_FIRSTNAME, cursor.getString(1));
            user.put(KEY_LASTNAME, cursor.getString(2));
            user.put(KEY_EMAIL, cursor.getString(3));
            user.put(KEY_PHONE, cursor.getString(4));
            user.put(KEY_UID, cursor.getString(5));
            user.put(KEY_ROLE, cursor.getString(6));
            user.put(KEY_CITY, cursor.getString(7));
            user.put(KEY_ACTIVE, (cursor.getInt(9) == 1)? ""+true: ""+false) ;
            user.put(KEY_APPROVED, (cursor.getInt(10) == 1)? ""+true: ""+false) ;
            user.put(KEY_CREATED_AT, cursor.getString(11));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }


    public HashMap<String, String> getUserDetailsByEmail(String email) {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE email = '"+ email + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("firstname", cursor.getString(1));
            user.put("lastname", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("phone", cursor.getString(4));
            user.put("uid", cursor.getString(5));
            user.put("role", cursor.getString(6));
            user.put("city", cursor.getString(7));
            user.put("active", (cursor.getInt(9) == 1)? "true": "false") ;
            user.put("approved", (cursor.getInt(10) == 1)? "true": "false") ;
            user.put("created_at", cursor.getString(11));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }
    /**
     * Getting user data from database
     * */
    public String getUserRole(String uid) {
        String selectQuery = "SELECT role FROM " + TABLE_USER + " WHERE uid = '"+ uid + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        String str = "";
        if (cursor.getCount() > 0) {
            str = cursor.getString(0);
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user role from Sqlite: " + uid);

        return str;
    }

    public String getUserRoleByEmail(String email) {
        String selectQuery = "SELECT role FROM " + TABLE_USER + " WHERE email = '"+ email+ "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        String str = "";
        if (cursor.getCount() > 0) {
            str = cursor.getString(1);
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user role from Sqlite: " + email);

        return str;
    }

    public String getApiKeyByEmail(String email) {
        String selectQuery = "SELECT "+KEY_API+" FROM " + TABLE_USER + " WHERE email = '"+ email+ "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        String str = "";
        if (cursor.getCount() > 0) {
            str = cursor.getString(1);
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching api from Sqlite: " + email);

        return str;
    }
    public String getUserApi() {
        String str = null;
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            str = cursor.getString(8);
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching "+KEY_API+" from Sqlite: "+str);

        return str;
    }
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void activatedUsers(String email){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues update = new ContentValues();
        update.put(KEY_ACTIVE, 1);

        int i = db.update(TABLE_USER, update, "email = '"+email+"'", null);
        db.close();
        Log.d(TAG, "activatedUsers ( "+ i +" )into  sqlite" + email);
    }

    public void userApproved(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues update = new ContentValues();
        update.put(KEY_APPROVED, 1);

        int i = db.update(TABLE_USER, update, "email = '"+email+"'", null);
        db.close();
        Log.d(TAG, "userApproved ( "+ i +" ) into sqlite"+email);
    }



    public User toUser(HashMap<String, String> params){
        User user = new User();
        user.setFirstname(params.get(KEY_FIRSTNAME));
        user.setLastname(params.get(KEY_LASTNAME));
        user.setEmail(params.get(KEY_EMAIL));
        user.setPhone(params.get(KEY_PHONE));
        user.setUid(params.get(KEY_UID));
        user.setRole(params.get(KEY_ROLE));
        user.setCity(params.get(KEY_CITY));

        return user;
    }

    public void updateUser(String email, String role, boolean active, boolean approved) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_ROLE, role);
        values.put(KEY_ACTIVE, (active ? 1:0));
        values.put(KEY_APPROVED, (approved ? 1:0));

        String where = "email = ?";
        String[] whereArgs = {email};
        long id = db.update(TABLE_USER, values, where, whereArgs);
        db.close(); // Closing database connection

        Log.d(TAG, "User data updated into sqlite: " + id);
    }

    public void updateUserCity(String email, String city) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_ROLE, city);

        String where = "email = ?";
        String[] whereArgs = {email};
        long id = db.update(TABLE_USER, values, where, whereArgs);
        db.close(); // Closing database connection

        Log.d(TAG, "User data updated into sqlite: " + id);
    }

}