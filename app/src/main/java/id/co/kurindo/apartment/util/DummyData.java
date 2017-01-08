package id.co.kurindo.apartment.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.co.kurindo.apartment.R;
import id.co.kurindo.apartment.model.Billing;
import id.co.kurindo.apartment.model.History;
import id.co.kurindo.apartment.model.Person;
import id.co.kurindo.apartment.model.Pilihan;
import id.co.kurindo.apartment.model.Product;
import id.co.kurindo.apartment.model.User;

/**
 * Created by aspire on 12/23/2016.
 */

public class DummyData {
    public static Map<String, User> MAP_USERS = new HashMap<>();
    public static List<User> users = new ArrayList<>();
    public static Map<String, Product> MAP_MENUS = new HashMap<>();
    public static List<Product> menus = new ArrayList<>();
    public static Map<String, History> MAP_HISTORIES = new HashMap<>();
    public static List<History> histories= new ArrayList<>();
    public static Map<String, History> MAP_NEWS = new HashMap<>();
    public static List<History> news= new ArrayList<>();
    public static Map<String, Billing> MAP_BILLINGS = new HashMap<>();
    public static List<Billing> billings= new ArrayList<>();
    public static User user;

    static {
        user = new User(1, "Fadhil","Kheir", "fadhillah.kheir@gmail.com", "6282110056018");
        addUser(new User(2, "Dwi","Miyanto", "edui.bin@gmail.com", "6282110056018"));

        Product p = new Product(R.drawable.water_xxl, "Water");
        p.getItems().add( new Pilihan("A", "Air tidak ada"));
        p.getItems().add( new Pilihan("B", "Air yang keluar kecil"));
        p.getItems().add( new Pilihan("C", "Air tidak bisa dimatikan"));
        p.setCost(100000);
        addMenu(p);

        p = new Product(R.drawable.gas_512, "Gas");
        p.getItems().add( new Pilihan("A", "Gas bocor"));
        p.getItems().add( new Pilihan("B", "Gas tidak ada"));
        p.getItems().add( new Pilihan("C", "Gas yang keluar kecil"));
        p.getItems().add( new Pilihan("D", "Gas tidak bisa dimatikan"));
        p.setCost(100000);
        addMenu(p);
        p = new Product(R.drawable.internet, "Internet & TV");
        p.getItems().add( new Pilihan("A", "Internet tidak bisa konek"));
        p.getItems().add( new Pilihan("B", "Koneksi internet lambat"));
        p.getItems().add( new Pilihan("C", "Tidak ada siaran TV"));
        p.setCost(100000);
        addMenu(p);
        p = new Product(R.drawable.images, "Electric");
        p.getItems().add( new Pilihan("A", "Jaringan listrik tidak ada"));
        p.getItems().add( new Pilihan("B", "Arus listrik lemah"));
        p.setCost(100000);
        addMenu(p);
        p = new Product(R.drawable.fire_est, "Fire");
        addMenu(p);
        p = new Product(R.drawable.security_badge, "Security");
        p.getItems().add( new Pilihan("A", "Ada keributan"));
        p.getItems().add( new Pilihan("B", "Ada orang mencurigakan"));
        addMenu(p);

        History h = new History("10 Desember 2016", "Water Report\n"    +
                "Jenis Aduan : Air tidak ada\n" +
                "Waktu Laporan : 10 Desember 2016, 15.00 WIB\n" +
                "Status : sudah normal\n" +
                "Waktu : 10 Desember 2016 ,15.30 WIB\n" +
                "PIC : Beyok\n");//*/
        addHistory(h);

        h = new History("1 Desember 2016", "Internet Report");
        addHistory(h);

        h = new History("2 Desember 2016", "Security Report");
        addHistory(h);

        History n = new History("Desember", "Maintenance generator sehingga berimpak pemadaman lampu pada hari kamis tanggal 10 Desember 2016 pada pukul 09.00 - 11.00 WIB");
        addNews(n);

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
    private static void addMenu(Product menu) {
        menus.add(menu);
        MAP_MENUS.put(""+menu.getDrawable(), menu);
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
