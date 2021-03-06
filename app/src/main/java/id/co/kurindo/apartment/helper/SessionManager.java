package id.co.kurindo.apartment.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import id.co.kurindo.apartment.base.AppConfig;

/**
 * Created by DwiM on 11/8/2016.
 */
public class SessionManager {
        // LogCat tag
        private static String TAG = SessionManager.class.getSimpleName();

        // Shared Preferences
        SharedPreferences pref;

        SharedPreferences.Editor editor;
        Context _context;

        // Shared pref mode
        int PRIVATE_MODE = 0;

        // Shared preferences file name
        private static final String PREF_NAME = "KURINDOAppLogin";

        private static final String KEY_IS_AUTO_LOGGEDIN = "isAutoLoggedIn";
        private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
        private static final String KEY_IS_ACTIVATED= "isActivated";
        private static final String KEY_ROLE = "keyRole";
        private static final String KEY_CITY= "keyCity";
        private static final String KEY_PHONE = "keyPhone";
        private static final String KEY_TOKEN = "keyToken";
        private static final String KEY_ROOM = "keyRoom";

        public SessionManager(Context context) {
            this._context = context;
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }

        public void setAutoLogin(boolean isLoggedIn) {
            editor.putBoolean(KEY_IS_AUTO_LOGGEDIN, isLoggedIn);
            // commit changes
            editor.commit();
        }

        public void setLogin(boolean isLoggedIn) {
            editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
            // commit changes
            editor.commit();
        }
        public void setActive(boolean isActive) {
            editor.putBoolean(KEY_IS_ACTIVATED, isActive);
            // commit changes
            editor.commit();
        }


    public void setToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }
    public void setRoom(String room) {
        editor.putString(KEY_ROOM, room);
        editor.commit();
    }

    public void setLoginData(String role, String mobile) {

            editor.putString(KEY_ROLE, role);

            editor.putString(KEY_PHONE, mobile);

            // commit changes
            editor.commit();

            Log.d(TAG, "User login session modified!");
        }

        public void setLogout() {
            setLogin(false);
            setAutoLogin(false);
            setLoginData(null,null);
            setActive(true);
        }
        public boolean isAutoLoggedIn(){
            return pref.getBoolean(KEY_IS_AUTO_LOGGEDIN, false);
        }

        public boolean isActivated(){
            return pref.getBoolean(KEY_IS_ACTIVATED, false);
        }
        public boolean isLoggedIn(){
            return pref.getBoolean(KEY_IS_LOGGEDIN, false);
        }
        public void clear() {
        }

    public boolean isOwner() {
        String role = pref.getString(KEY_ROLE, "client");
        return (role.equals(AppConfig.KEY_OWNER) || role.equals(AppConfig.KEY_ADMIN) );
    }
    public boolean isTenant() {
        String role = pref.getString(KEY_ROLE, "client");
        return (role.equals(AppConfig.KEY_TENANT) );
    }
    public boolean isSupport() {
        String role = pref.getString(KEY_ROLE, "client");
        return (role.equals(AppConfig.KEY_ENGINEER) || role.equals(AppConfig.KEY_SECURITY));
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, null);
    }
    public String getRoom() {
        return pref.getString(KEY_ROOM, null);
    }
}
