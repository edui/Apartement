package id.co.kurindo.apartment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import id.co.kurindo.apartment.base.AppController;
import id.co.kurindo.apartment.helper.SessionManager;
import id.co.kurindo.apartment.model.Address;
import id.co.kurindo.apartment.model.Apartment;
import id.co.kurindo.apartment.model.Room;
import id.co.kurindo.apartment.model.User;


/**
 * Created by DwiM on 11/16/2016.
 */

public class SplashScreenActivity extends AppCompatActivity {
    boolean done = false;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        session = new SessionManager(getApplicationContext());
        Thread timer = new Thread() {
            public void run(){
                while(!done){
                    try {
                        String roomStr = session.getRoom();
                        String room = roomStr;
                        String apartment ="";
                        if(roomStr != null && roomStr.contains("|")){
                            room = roomStr.substring(0, roomStr.indexOf("|"));
                            apartment = roomStr.substring(roomStr.indexOf("|")+1);
                        }else{
                            room = "Select Room";
                        }
                        Room roomObj = new Room(room, new User());
                        roomObj.setApartment(new Apartment(1, apartment, new Address()));
                        AppController.getInstance().room = roomObj;
                        //AppController.getInstance().lang = session.getLang();

                        sleep(500);
                        done = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.start();
    }

}
