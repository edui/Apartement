package id.co.kurindo.apartment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by DwiM on 11/16/2016.
 */

public class SplashScreenActivity extends AppCompatActivity {
    boolean done = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread timer = new Thread() {
            public void run(){
                while(!done){
                    try {
                        sleep(500);
                        done = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.start();
    }

}
