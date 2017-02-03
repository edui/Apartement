package id.co.kurindo.apartment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import id.co.kurindo.apartment.base.KurindoActivity;

/**
 * Created by dwim on 1/7/2017.
 */

public class RoomActivity extends KurindoActivity {
    protected ActionBar setupToolbar() {
        ActionBar ab = super.setupToolbar();
        if(ab != null) ab.setTitle("My Rooms");
        return ab;
    }

    @Override
    public Class getFragmentClass() {
        return RoomFragment.class;
    }

    @Override
    public Bundle getBundleParams() {
        return getIntent().getExtras();
    }
}
