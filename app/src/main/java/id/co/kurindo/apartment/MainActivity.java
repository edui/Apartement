package id.co.kurindo.apartment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import id.co.kurindo.apartment.base.KurindoActivity;

/**
 * Created by aspire on 12/23/2016.
 */

public class MainActivity extends KurindoActivity {

    @Override
    public Class getFragmentClass() {
        return MainTabFragment.class;
    }

    @Override
    public Bundle getBundleParams() {
        return null;
    }

    protected ActionBar setupToolbar() {
        ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_domain_white_18dp);
        ab.setDisplayHomeAsUpEnabled(true);
        return ab;
    }
}
