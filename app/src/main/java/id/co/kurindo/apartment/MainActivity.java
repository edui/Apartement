package id.co.kurindo.apartment;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import id.co.kurindo.apartment.base.KurindoActivity;
import id.co.kurindo.apartment.base.KurindoBaseDrawerActivity;

/**
 * Created by aspire on 12/23/2016.
 */

public class MainActivity extends KurindoBaseDrawerActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragment(MainTabFragment.class);
    }
}
