package id.co.kurindo.apartment.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.co.kurindo.apartment.R;
import id.co.kurindo.apartment.base.AppConfig;
import id.co.kurindo.apartment.model.Address;
import id.co.kurindo.apartment.model.Apartment;
import id.co.kurindo.apartment.model.Billing;
import id.co.kurindo.apartment.model.History;
import id.co.kurindo.apartment.model.ImageModel;
import id.co.kurindo.apartment.model.Issue;
import id.co.kurindo.apartment.model.Pilihan;
import id.co.kurindo.apartment.model.Room;
import id.co.kurindo.apartment.model.User;

/**
 * Created by aspire on 12/23/2016.
 */

public class DummyData {
    public static Map<String, User> MAP_USERS = new HashMap<>();
    public static List<User> users = new ArrayList<>();
    public static Map<String, Issue> MAP_MENUS = new HashMap<>();
    public static List<Issue> menus = new ArrayList<>();
    public static Map<String, History> MAP_HISTORIES = new HashMap<>();
    public static List<History> histories= new ArrayList<>();
    public static List<History> newhistories= new ArrayList<>();
    public static List<History> iphistories= new ArrayList<>();
    public static List<History> completedhistories= new ArrayList<>();
    public static Map<String, History> MAP_NEWS = new HashMap<>();
    public static List<History> news= new ArrayList<>();
    public static Map<String, Billing> MAP_BILLINGS = new HashMap<>();
    public static List<Billing> billings= new ArrayList<>();
    public static Map<String, ImageModel> MAP_ADS= new HashMap<>();
    public static List<ImageModel> ads= new ArrayList<>();
    public static List<Apartment> apartments= new ArrayList<>();
    public static Map<String, Apartment> APARTMENT_ADS= new HashMap<>();
    public static User user;
    public static List<Room> rooms = new ArrayList<>();

    static {
        user = new User(1, "Fadhil","Kheir", "fadhillah.kheir@gmail.com", "6282110056018");
        User user2 = new User(2, "Dwi","Miyanto", "edui.bin@gmail.com", "6282110056018");
        addUser(user2);

        Apartment apartment = new Apartment(1, "Puri Apartment", new Address(1, "Jalan Raya", "Medan Merdeka", "Jakarta Pusat"));
        apartments.add(apartment);
        apartment.setRooms(rooms);

        Room r = new Room("R212", user);
        r.setApartment(apartment);
        rooms.add(r);
        r = new Room("R213", user);
        r.setApartment(apartment);
        rooms.add(r);
        apartment = new Apartment(1, "Grand Apartment", new Address(1, "Jalan Desa", "Dukuh Atas", "Jakarta Pusat"));
        apartments.add(apartment);
        apartment.setRooms(rooms);
        r = new Room("R214", user);
        r.setApartment(apartment);
        rooms.add(r);

        Issue p = new Issue(R.drawable.water_xxl, "Water");
        p.getItems().add( new Pilihan("A", "Air tidak ada"));
        p.getItems().add( new Pilihan("B", "Air yang keluar kecil"));
        p.getItems().add( new Pilihan("C", "Air tidak bisa dimatikan"));
        p.setCost(100000);
        addMenu(p);

        p = new Issue(R.drawable.gas_512, "Gas");
        p.getItems().add( new Pilihan("A", "Gas bocor"));
        p.getItems().add( new Pilihan("B", "Gas tidak ada"));
        p.getItems().add( new Pilihan("C", "Gas yang keluar kecil"));
        p.getItems().add( new Pilihan("D", "Gas tidak bisa dimatikan"));
        p.setCost(100000);
        addMenu(p);
        p = new Issue(R.drawable.internet, "Internet & TV");
        p.getItems().add( new Pilihan("A", "Internet tidak bisa konek"));
        p.getItems().add( new Pilihan("B", "Koneksi internet lambat"));
        p.getItems().add( new Pilihan("C", "Tidak ada siaran TV"));
        p.setCost(100000);
        addMenu(p);
        p = new Issue(R.drawable.images, "Electric");
        p.getItems().add( new Pilihan("A", "Jaringan listrik tidak ada"));
        p.getItems().add( new Pilihan("B", "Arus listrik lemah"));
        p.setCost(100000);
        addMenu(p);
        p = new Issue(R.drawable.fire_est, "Fire");
        addMenu(p);
        p = new Issue(R.drawable.security_badge, "Security");
        p.getItems().add( new Pilihan("A", "Ada keributan"));
        p.getItems().add( new Pilihan("B", "Ada orang mencurigakan"));
        addMenu(p);
        Issue issue = new Issue("1", "Water Report", "Air tidak ada", "Air tidak ada", new Room("212", new User()), AppConfig.STATUS_CLOSED, System.currentTimeMillis());
        /*History h = new History("10 Desember 2016", "Water Report\n"    +
                "Jenis Aduan : Air tidak ada\n" +
                "Waktu Laporan : 10 Desember 2016, 15.00 WIB\n" +
                "Status : sudah normal\n" +
                "Waktu : 10 Desember 2016 ,15.30 WIB\n" +
                "PIC : Beyok\n", STATUS_COMPLETED);//*/
        History h = new History(issue.getReportDate(), issue);
        addHistory(h);
        completedhistories.add(h);

        //h = new History("1 Desember 2016", "Internet Report", STATUS_NEW);
        issue = new Issue("2", "Internet & TV", "Internet Report", "Internet lambat", new Room("212", new User()), AppConfig.STATUS_REVIEW, System.currentTimeMillis());
        h = new History(issue.getReportDate(), issue);
        addHistory(h);
        newhistories.add(h);

        //h = new History("2 Desember 2016", "Security Report", STATUS_IP);
        issue = new Issue("3", "Security", "Security Report", "Security lambat", new Room("212", new User()), AppConfig.STATUS_PROGRESS, System.currentTimeMillis());
        h = new History(issue.getReportDate(), issue);
        addHistory(h);
        iphistories.add(h);

        issue = new Issue("4", "Maintenance", "Maintenance News", "Maintenance generator sehingga berimpak pemadaman lampu pada hari kamis tanggal 10 Desember 2016 pada pukul 09.00 - 11.00 WIB", null, AppConfig.STATUS_PROGRESS, System.currentTimeMillis());
        History n = new History(issue.getReportDate(), issue);
        addNews(n);
        iphistories.add(n);

        Billing b =new Billing("2016", "Desember", menus, "25 Desember 2016", "24 Desember 2016");
        addBilling(b);
        b =new Billing("2016", "November");
        addBilling(b);
        b =new Billing("2016", "Oktober");
        addBilling(b);
        b =new Billing("2016", "September");
        addBilling(b);
        b =new Billing("2016", "Agustus");
        addBilling(b);
        b =new Billing("2016", "Juli");
        addBilling(b);
        b =new Billing("2016", "Juni");
        addBilling(b);
        b =new Billing("2016", "May");
        addBilling(b);
        b =new Billing("2016", "April");
        addBilling(b);
        b =new Billing("2016", "Maret");
        addBilling(b);
        b =new Billing("2016", "Februari");
        addBilling(b);
        b =new Billing("2016", "Januari");
        addBilling(b);

        addItem(new ImageModel(R.drawable.banner_dosend, "DO_SEND"));
        addItem(new ImageModel(R.drawable.banner_dojek, "DO_JEK"));
        addItem(new ImageModel(R.drawable.banner_dowash, "DO_WASH"));
        addItem(new ImageModel(R.drawable.a, "DO_SERVICE"));
        addItem(new ImageModel(R.drawable.b, "DO-HIJAMAH"));
        addItem(new ImageModel(R.drawable.banner_docar, "D_CAR"));
        addItem(new ImageModel(R.drawable.c, "Do-MOVE"));
        addItem(new ImageModel(R.drawable.d, "DO-CLIENT"));
        addItem(new ImageModel(R.drawable.e, "DO-CLIENT"));
    }

    public static void addHistory(History h) {
        histories.add(h);
        MAP_HISTORIES.put(h.getDate(), h);
    }
    public static void addBilling(Billing b) {
        billings.add(b);
        MAP_BILLINGS.put(b.getYear()+""+b.getMonth(), b);
    }

    private static void addNews(History h) {
        news.add(h);
        MAP_NEWS.put(h.getDate(), h);
    }

    public static void addUser(User user) {
        users.add(user);
        MAP_USERS.put(user.getPhone(), user);
    }
    private static void addMenu(Issue menu) {
        menus.add(menu);
        MAP_MENUS.put(""+menu.getName(), menu);
    }
    private static void addItem(ImageModel menu) {
        ads.add(menu);
        MAP_ADS.put(""+menu.getDrawable(), menu);
    }

    public static String formatCurrency(double amount)
    {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        format.setGroupingUsed(true);
        //Currency currency = Currency.getInstance(Constant.CURRENCY);
        //format.setCurrency(currency);
        return "Rp "+ ""+format.format(amount);
    }

}
