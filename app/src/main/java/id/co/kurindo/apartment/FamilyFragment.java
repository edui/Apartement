package id.co.kurindo.apartment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import net.rimoto.intlphoneinput.IntlPhoneInput;

import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import id.co.kurindo.apartment.base.BaseFragment;
import id.co.kurindo.apartment.model.Person;
import id.co.kurindo.apartment.model.User;
import id.co.kurindo.apartment.util.DummyData;

/**
 * Created by aspire on 12/23/2016.
 */

public class FamilyFragment extends BaseFragment {
    @Bind(R.id.input_firstname)
    EditText inputFirstname;
    @Bind(R.id.input_lastname)
    EditText inputLastname;
    @Bind(R.id.input_email)
    EditText inputEmail;
    @Bind(R.id.input_phone)
    IntlPhoneInput phoneNumber;
    @Bind(R.id.btn_submit)
    AppCompatButton btnSubmit;
    Person user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        user = b.getParcelable("user");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflateAndBind(inflater, container, R.layout.fragment_family);
        if(user != null){
            inputLastname.setText(user.getLastname());
            inputFirstname.setText(user.getFirstname());
            inputEmail.setText(user.getEmail());
            if(user.getPhone().startsWith("+"))
                phoneNumber.setNumber((user.getPhone().startsWith("+")? "":"+")+user.getPhone());
        }
        return v;
    }

    @OnClick(R.id.btn_submit)
    public void onClick_BtnSubmit(){
        if(!valid()){
            return;
        }
        Random r = new Random();
        User p = new User();
        p.setId(r.nextInt());
        p.setFirstname(inputFirstname.getText().toString());
        p.setLastname(inputLastname.getText().toString());
        p.setEmail(inputEmail.getText().toString());
        p.setPhone("+"+phoneNumber.getPhoneNumber().getCountryCode()+""+phoneNumber.getNumber());

        DummyData.addUser(p);

        Toast.makeText(getContext(), "user baru berhasil ditambahkan.", Toast.LENGTH_SHORT);
        getActivity().finish();
    }

    private boolean valid() {
        boolean valid = true;
        String email = inputEmail.getText().toString();
        String firstname = inputFirstname.getText().toString();
        String lastname = inputLastname.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("masukkan email yang valid");
            valid = false;
        } else {
            inputEmail.setError(null);
        }
        if(firstname == null || firstname.isEmpty() ){
            inputFirstname.setError("masukkan nama depan");
            valid = false;
        }else{
            inputFirstname.setError(null);
        }
        if(lastname== null || lastname.isEmpty() ){
            inputLastname.setError("masukkan nama belakang");
            valid = false;
        }else{
            inputLastname.setError(null);
        }
        if(!phoneNumber.isValid()){
            valid = false;
            Toast.makeText(getContext(), "Masukkan Nomor handphone yang valid.", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }
}
