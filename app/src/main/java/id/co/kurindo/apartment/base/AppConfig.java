package id.co.kurindo.apartment.base;

import java.text.SimpleDateFormat;

/**
 * Created by DwiM on 5/6/2017.
 */

public class AppConfig {
    public static String API_VERSION = "apart_v0.1";
    public static String BASE_HOST = "https://api.hijr.co.id";//+"/"+API_VERSION;
    //public static String BASE_HOST = "http://172.30.3.58/kurindo";
    public static String API_HOST = BASE_HOST + "/" + API_VERSION;
    public static String HOST = API_HOST;

    public static String URL_LOGIN = HOST + "/auth/login";
    public static String URL_USER_DELATION_SUBMIT = HOST + "/user/delation/submit";
    public static String URL_USER_DELATION_VIEW = HOST + "/user/delation/{id}";
    public static String URL_USER_DELATION_LIST = HOST + "/user/delation/list/{category}";
    public static String URL_USER_DELATION_UPDATE = HOST + "/user/delation/{id}/update";
    public static String URL_RETRIEVE_ROOM_DEFAULT = HOST + "/user/room/{type}";
    public static String URL_RETRIEVE_ROOM_LIST = HOST + "/ref/room";


    public static String KEY_ADMIN = "ADMIN";
    public static String KEY_OWNER = "OWNER";
    public static String KEY_TENANT = "TENANT";
    public static String KEY_ENGINEER = "ENGINEER";
    public static String KEY_SECURITY = "SECURITY";

    public static String STATUS_CLOSED = "CLOSED";
    public static String STATUS_REVIEW = "REVIEW";
    public static String STATUS_PROGRESS = "PROGRESS";

    public static SimpleDateFormat getSdf() {
        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
        return format;
    }

    public static boolean isClosed(String status) {
        return status.equalsIgnoreCase(STATUS_CLOSED);
    }

    public static boolean isNew(String status) {
        return status.equalsIgnoreCase(STATUS_REVIEW);
   }

    public static boolean isInProgress(String status) {
        return status.equalsIgnoreCase(STATUS_PROGRESS) ;
    }
}
