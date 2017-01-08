package id.co.kurindo.apartment;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.kurindo.apartment.util.MaskedEditText;

/**
 * Created by aspire on 12/23/2016.
 */

public class ActivationActivity extends AppCompatActivity {
    @Bind(R.id.btn_signup)
    AppCompatButton signUpBtn;
    @Bind(R.id.input_phone)
    IntlPhoneInput phoneNumber;

    @Bind(R.id.maskedEditText)
    MaskedEditText maskedEditText;

    @Bind(R.id.maskedEditTextLayout)
    TextInputLayout maskedEditTextLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        String myIntlPhoneNumber = getIntent().getExtras().getString("phone");
        //phoneNumber.setNumber(myIntlPhoneNumber);

        maskedEditTextLayout.setVisibility(View.VISIBLE);
        maskedEditText.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_signup)
    public void OnActivated(View v) {
        if(!validate()) {
            Toast.makeText(getApplicationContext(), "Invalid",Toast.LENGTH_LONG);
            return;
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    private boolean validate() {
        boolean valid = true;

        if(maskedEditText.getText() == null || maskedEditText.getText().length() != 4){
            maskedEditText.setError("Inputkan 4 karakter code");
            valid = false;
        }else{
            maskedEditText.setError(null);
        }
        return valid;
    }
}
