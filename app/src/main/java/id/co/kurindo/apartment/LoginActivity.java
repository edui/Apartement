package id.co.kurindo.apartment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lamudi.phonefield.PhoneInputLayout;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.kurindo.apartment.base.AppConfig;
import id.co.kurindo.apartment.base.AppController;
import id.co.kurindo.apartment.base.BaseActivity;
import id.co.kurindo.apartment.helper.ProgressDialogCustom;
import id.co.kurindo.apartment.helper.SQLiteHandler;
import id.co.kurindo.apartment.helper.SessionManager;
import id.co.kurindo.apartment.model.Room;
import id.co.kurindo.apartment.model.User;
import id.co.kurindo.apartment.util.DataParser;
import id.co.kurindo.apartment.util.DummyData;
import id.co.kurindo.apartment.util.LogUtil;

/**
 * Created by DwiM on 5/12/2017.
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    @Bind(R.id.btn_login)
    AppCompatButton btnLogin;
    @Nullable @Bind(R.id.chkAutoLogin)
    AppCompatCheckBox _chkAutoLogin;

    @Bind(R.id.tvRegister)
    TextView tvRegister;

    @Bind(R.id.input_phone)
    PhoneInputLayout phoneNumber;
    @Bind(R.id.input_password)
    EditText _passwordText;

    SessionManager session;
    SQLiteHandler db;
    ProgressDialogCustom progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialogCustom(this);
        phoneNumber.setDefaultCountry("ID");
        phoneNumber.setHint(R.string.phoneNumber);

        session = new SessionManager(this);
        db = new SQLiteHandler(this);
        if(session.isLoggedIn()) {
            onLoginSuccess();
        }

    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }
    public void onLoginSuccess(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @OnClick(R.id.tvRegister)
    public void OnRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_login)
    public void OnLogin(){
        LogUtil.logD(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        btnLogin.setEnabled(false);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String phone = phoneNumber.getPhoneNumber();
        String password = _passwordText.getText().toString();
        String phoneStr = "0"+phone.substring(3);
        //Implementation authentication logic here.
        checkLogin(phoneStr, password);
/*
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        session.setLogin(true);
                        session.setLoginData(AppConfig.KEY_OWNER, phone);
                        session.setAutoLogin(_chkAutoLogin.isChecked());
                        db.onUpgrade(db.getWritableDatabase(), 0, 1);
                        db.addUser("Fadhillah","Kheir", "fadhil.kheir@gmail.com",phone,"1", AppConfig.KEY_OWNER,"JKT","api",true, true, "1-jan-2017");
                        DummyData.user.setPhone("+"+phone);

                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
*/
    }

    private void checkLogin(String phone, String password) {
        String tag_string_req = "req_login";
        String url = AppConfig.URL_LOGIN;
        final HashMap params = new HashMap();
        params.put("userid", phone);
        params.put("password", password);
        HashMap headers = new HashMap();
        addRequest(tag_string_req, Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogUtil.logD(TAG, "Login > checkLogin Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    String message = ""+jObj.getString("message");
                    int status = jObj.getInt("status");
                    if(status == 200){
                        JSONObject data = jObj.getJSONObject("data");
                        if(data != null){
                            DataParser parser = new DataParser();
                            String token = data.getString("token");
                            JSONObject userObj = data.getJSONObject("user");
                            User user = parser.parseUser(userObj);
                            user.setToken(token);

                            db.onUpgrade(db.getWritableDatabase(), 0, 1);
                            db.addUser(user.getFirstname(),user.getLastname(), user.getEmail(), user.getPhone(),""+user.getId(), user.getRole(),"JKT",user.getToken(),true, true, "1-jan-2017");
                            DummyData.user.setPhone(""+user.getPhone());

                            session.setLogin(true);
                            session.setLoginData(user.getRole(), user.getPhone());
                            session.setToken(user.getToken());
                            session.setAutoLogin(_chkAutoLogin.isChecked());
                        }
                        onLoginSuccess();
                    }else{
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    LogUtil.logE(TAG, ""+e.getMessage());
                }
                btnLogin.setEnabled(true);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                LogUtil.logE(TAG, ""+e.getMessage());
                btnLogin.setEnabled(true);
                progressDialog.dismiss();
            }
        }, params, headers);
    }

    private void onLoginFailed() {

    }

    private boolean validate() {
        boolean valid = true;
        if(!phoneNumber.isValid()){
            phoneNumber.setError("Telepon tidak valid.");
            valid =false;
        }else{
            phoneNumber.setError(null);
        }
        String passwd = _passwordText.getText().toString();
        if(passwd.isEmpty()){
            _passwordText.setError("Password tidak valid");
            valid =false;
        }else{
            _passwordText.setError(null);
        }
        return valid;
    }
}
