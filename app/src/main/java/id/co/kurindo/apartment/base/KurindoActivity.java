package id.co.kurindo.apartment.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import id.co.kurindo.apartment.R;
import id.co.kurindo.apartment.helper.SessionManager;


/**
 * Created by DwiM on 12/14/2016.
 */

public abstract class KurindoActivity extends BaseActivity {
    private Class fragmentClass;
    protected SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        session = new SessionManager(this);

        showFragment(getFragmentClass(), getBundleParams(), getContainer());
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    public int getLayout() {
        return R.layout.activity_base_layout;
    }

    public abstract Class getFragmentClass();

    @Override
    protected ActionBar setupToolbar() {
        ActionBar ab = super.setupToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_18dp);
        return  ab;
    }

    public abstract Bundle getBundleParams();

    public int getContainer() {
        return R.id.containerView;
    }
}
