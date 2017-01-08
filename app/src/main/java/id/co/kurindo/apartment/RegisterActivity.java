package id.co.kurindo.apartment;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.kurindo.apartment.util.DummyData;
import id.co.kurindo.apartment.util.MaskedEditText;

/**
 * Created by aspire on 12/23/2016.
 */

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.btn_signup)
    AppCompatButton signUpBtn;
    @Bind(R.id.input_phone)
    IntlPhoneInput phoneNumber;

    @Bind(R.id.maskedEditText)
    MaskedEditText maskedEditText;

    @Bind(R.id.maskedEditTextLayout)
    TextInputLayout maskedEditTextLayout;

    @Bind(R.id.layoutVerifikasi)
    LinearLayout layoutVerifikasi;

    boolean activation = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        layoutVerifikasi.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_signup)
    public void OnSignUp(View v){
        String myInternationalNumber =DummyData.user.getPhone();
        if(!activation){
            if(!phoneNumber.isValid()){
                Toast.makeText(getApplicationContext(), "Invalid Phone",Toast.LENGTH_SHORT);
                return;
            }
            myInternationalNumber = phoneNumber.getPhoneNumber().getCountryCode()+ ""+phoneNumber.getPhoneNumber().getNationalNumber();
            int countrycode = phoneNumber.getPhoneNumber().getCountryCode();
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
        DummyData.user.setPhone("+"+myInternationalNumber);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

}
