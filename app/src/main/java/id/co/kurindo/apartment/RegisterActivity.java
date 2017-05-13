package id.co.kurindo.apartment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.lamudi.phonefield.PhoneInputLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.kurindo.apartment.base.AppController;
import id.co.kurindo.apartment.helper.SQLiteHandler;
import id.co.kurindo.apartment.helper.SessionManager;
import id.co.kurindo.apartment.util.DummyData;
import id.co.kurindo.apartment.util.MaskedEditText;

/**
 * Created by aspire on 12/23/2016.
 */

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.btn_signup)
    AppCompatButton signUpBtn;
    @Bind(R.id.input_phone)
    PhoneInputLayout phoneNumber;

    @Bind(R.id.maskedEditText)
    MaskedEditText maskedEditText;

    @Bind(R.id.maskedEditTextLayout)
    TextInputLayout maskedEditTextLayout;

    @Bind(R.id.layoutVerifikasi)
    LinearLayout layoutVerifikasi;
    @Bind(R.id.spPilihBahasa)
    Spinner spPilihBahasa;

    boolean activation = false;
    SessionManager session;
    SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        layoutVerifikasi.setVisibility(View.GONE);

        session = new SessionManager(this);
        db = new SQLiteHandler(this);
        phoneNumber.setDefaultCountry("ID");
        phoneNumber.setHint(R.string.phoneNumber);
        if(session.isLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.pilihan_bahasa_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPilihBahasa.setAdapter(adapter);
        spPilihBahasa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String [] langs = getResources().getStringArray(R.array.pilihan_bahasa_array);
                AppController.getInstance().lang = langs[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.btn_signup)
    public void OnSignUp(View v){
        String myInternationalNumber =DummyData.user.getPhone();
        if(!activation){
            if(!phoneNumber.isValid()){
                Toast.makeText(getApplicationContext(), "Invalid Phone",Toast.LENGTH_SHORT);
                return;
            }
            myInternationalNumber = phoneNumber.getPhoneNumber();
            //int countrycode = phoneNumber.getPhoneNumber().getCountryCode();
            /*Bundle bundle = new Bundle();
            bundle.putString("phone", myInternationalNumber);
            bundle.putInt("country", countrycode);

            //show activity activation
            Intent intent = new Intent(getApplicationContext(), ActivationActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            */

            layoutVerifikasi.setVisibility(View.VISIBLE);

            Toast.makeText(this, "Activation code has been sent to your phone."+myInternationalNumber, Toast.LENGTH_SHORT).show();

            activation = true;
        }
        String mask = String.valueOf(maskedEditText.getText());
        try {
            Integer.parseInt(mask);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Invalid Code",Toast.LENGTH_SHORT);
            return;
        }
        session.setLogin(true);
        db.addUser("Fadhillah","Kheir", "fahil.kheir@gmail.com",myInternationalNumber,"1", "owner","JKT","api",true, true, "1-jan-2017");
        DummyData.user.setPhone("+"+myInternationalNumber);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tvLogin)
    public void OnLogin(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    }
