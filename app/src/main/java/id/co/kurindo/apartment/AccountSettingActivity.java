package id.co.kurindo.apartment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import id.co.kurindo.apartment.base.KurindoActivity;

/**
 * Created by aspire on 12/29/2016.
 */

public class AccountSettingActivity extends KurindoActivity {

    protected ActionBar setupToolbar() {
        ActionBar ab = super.setupToolbar();
        if(ab != null) ab.setTitle("Account Setting");
        return ab;
    }

        @Override
    public Class getFragmentClass() {
        return AccountSettingFragment.class;
    }

    @Override
    public Bundle getBundleParams() {
        return null;
    }
}
