package id.co.kurindo.apartment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import id.co.kurindo.apartment.base.AppConfig;
import id.co.kurindo.apartment.base.KurindoActivity;

/**
 * Created by dwim on 1/7/2017.
 */

public class HistoryActivity extends KurindoActivity {
    protected ActionBar setupToolbar() {
        ActionBar ab = super.setupToolbar();
        if(ab != null) ab.setTitle("History");
        return ab;
    }

    @Override
    public Class getFragmentClass() {
        //Bundle b = getIntent().getExtras();
        //if(b != null){
            //String role = b.getString("role");
            if(session.isTenant()){
                return HistoryTabFragment.class;
            }
        //}
        return HistoryJobTabFragment.class;
    }

    @Override
    public Bundle getBundleParams() {
        return getIntent().getExtras();
    }
}
