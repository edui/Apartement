package id.co.kurindo.apartment.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import id.co.kurindo.apartment.AccountSettingActivity;
import id.co.kurindo.apartment.HistoryActivity;
import id.co.kurindo.apartment.LoginActivity;
import id.co.kurindo.apartment.R;
import id.co.kurindo.apartment.RegisterActivity;
import id.co.kurindo.apartment.RoomActivity;
import id.co.kurindo.apartment.helper.SQLiteHandler;
import id.co.kurindo.apartment.helper.SessionManager;
import id.co.kurindo.apartment.model.Room;


/**
 * Created by aspire on 11/24/2016.
 */

public class KurindoBaseDrawerActivity extends BaseDrawerActivity {
    protected SessionManager session;
    protected SQLiteHandler db;
    protected AppCompatButton loginBtn;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // SQLite database handler
        db = new SQLiteHandler(this);
        //db.onUpgrade(db.getWritableDatabase(),0,1);
        // Session manager
        session = new SessionManager(this);
        initialize();
    }
    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            default:
            //case android.R.id.home:
                openDrawer();
                return true;

            //default:  return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Handles the navigation item click and starts the corresponding activity.
     * @param item the selected navigation item
     */
    protected void goToNavDrawerItem(int item) {
        Bundle bundle = new Bundle();
        switch (item) {
            case R.id.nav_item_logout:
                logoutUser();
                showActivity(LoginActivity.class);
                finish();
                break;
            case R.id.nav_item_account:
                showActivity(AccountSettingActivity.class, bundle);
                break;
            case R.id.nav_item_history:
                bundle.putString("role", "engineer");
                showActivity(HistoryActivity.class, bundle);
                break;
            case R.id.nav_item_history2:
                bundle.putString("role", "owner");
                showActivity(HistoryActivity.class, bundle);
                break;
            case R.id.nav_item_room:
                showActivity(RoomActivity.class);
                break;
        }
    }

    protected void initialize(){
        boolean owner= false;
        boolean support= false;
        boolean tenant = false;
        // Check if user is already logged in or not
        if (session.isLoggedIn())
        {
            if(session.isOwner()){
                owner = true;
                tenant = true;
            }
            if(session.isTenant()){
                tenant = true;
            }
            if(session.isSupport()){
                support = true;
            }
            setupHeader(true);
        }else{
            setupHeader(false);
        }
        navigationView.getMenu().setGroupVisible(R.id.group_owner_id, owner);
        navigationView.getMenu().setGroupVisible(R.id.group_tenant_id, tenant);
        navigationView.getMenu().setGroupVisible(R.id.group_support_id, support);
    }
    public void setupHeader(boolean param){
        View headerLayout = navigationView.getHeaderView(0);
        TextView txt = (TextView) headerLayout.findViewById(R.id.headerTxt);
        loginBtn = (AppCompatButton) headerLayout.findViewById(R.id.mainLoginBtn);
        if(param){
            HashMap user = db.getUserDetails();
            txt.setText(user.get("firstname") + " " + user.get("lastname") );
            navigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(true);
            loginBtn.setVisibility(View.GONE);
            navigationView.getMenu().findItem(R.id.nav_item_loggedin_as).setTitle("You're logged as "+user.get("role"));

        }else{
            txt.setText("Silahkan Login");
            navigationView.getMenu().findItem (R.id.nav_item_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_item_loggedin_as).setTitle("");
            loginBtn.setVisibility(View.VISIBLE);
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showActivity(RegisterActivity.class);
                }
            });
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutTitle);
        if(layout != null){
            layout.setVisibility(View.VISIBLE);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showActivity(RoomActivity.class);
                }
            });
        }
        TextView titleOne = (TextView) findViewById(R.id.titleOne);
        if(titleOne != null ){
            titleOne.setText((AppController.getInstance().room == null? "Apartment" : AppController.getInstance().room.getApartment().getName()));
        }
        TextView titleTwo = (TextView) findViewById(R.id.titleTwo);
        if(titleTwo != null ){
            titleTwo.setText("Room" +(AppController.getInstance().room == null? "" : AppController.getInstance().room.getRoomNumber()));
        }
    }
    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user packets from sqlite users table
     * */
    private void logoutUser() {
        session.setLogout();
        //db.deleteUsers();

        initialize();
    }
    @Override
    protected void onResume() {
        super.onResume();
        initialize();
        registerReceiver(receiver, new IntentFilter(Room.ROOM_CHANGED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                setupHeader(session.isLoggedIn());
            }
        }
    };

}
