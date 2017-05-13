package id.co.kurindo.apartment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lamudi.phonefield.PhoneInputLayout;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import id.co.kurindo.apartment.adapter.RoomAdapter;
import id.co.kurindo.apartment.base.AppConfig;
import id.co.kurindo.apartment.base.AppController;
import id.co.kurindo.apartment.base.BaseFragment;
import id.co.kurindo.apartment.helper.ProgressDialogCustom;
import id.co.kurindo.apartment.model.Person;
import id.co.kurindo.apartment.model.Room;
import id.co.kurindo.apartment.model.User;
import id.co.kurindo.apartment.util.DataParser;
import id.co.kurindo.apartment.util.DummyData;
import id.co.kurindo.apartment.util.LogUtil;

/**
 * Created by aspire on 12/23/2016.
 */

public class FamilyFragment extends BaseFragment {
    private static final String TAG = "FamilyFragment";

    @Bind(R.id.input_firstname)
    EditText inputFirstname;
    @Bind(R.id.input_lastname)
    EditText inputLastname;
    @Bind(R.id.input_email)
    EditText inputEmail;
    @Bind(R.id.input_phone)
    PhoneInputLayout phoneNumber;
    @Bind(R.id.spinnerRoom)
    Spinner selectRoom;
    @Bind(R.id.btn_submit)
    AppCompatButton btnSubmit;
    Person user;
    List<Room> rooms = new ArrayList<>();
    Room selectedRoom;
    boolean editMode = false;
    RoomAdapter roomAdapter;
    ProgressDialogCustom progressDialog;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if(b != null){
            user = b.getParcelable("user");
            editMode = true;
        }
        context = getContext();
        progressDialog = new ProgressDialogCustom(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflateAndBind(inflater, container, R.layout.fragment_family);
        if(user != null){
            inputLastname.setText(user.getLastname());
            inputFirstname.setText(user.getFirstname());
            inputEmail.setText(user.getEmail());
            //if(user.getPhone().startsWith("+")) phoneNumber.setNumber((user.getPhone().startsWith("+")? "":"+")+user.getPhone());
            phoneNumber.setPhoneNumber(user.getPhone());
        }
        phoneNumber.setDefaultCountry("ID");
        //rooms.addAll(DummyData.rooms);
        if(AppController.getInstance().rooms == null) retrieve_rooms();
        else rooms = AppController.getInstance().rooms;
        roomAdapter = new RoomAdapter(context, rooms);
        selectRoom.setAdapter(roomAdapter);
        selectRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRoom = (Room) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }

    @OnClick(R.id.btn_submit)
    public void onClick_BtnSubmit(){
        if(!valid()){
            return;
        }
        Random r = new Random();
        User p = null;
        if(editMode) p = DummyData.MAP_USERS.get(user.getPhone());
        if(p == null) {
            p = new User();
            p.setId(r.nextInt());
        }
        p.setFirstname(inputFirstname.getText().toString());
        p.setLastname(inputLastname.getText().toString());
        p.setEmail(inputEmail.getText().toString());
        p.setPhone(phoneNumber.getPhoneNumber());
        //p.setPhone("+"+phoneNumber.getPhoneNumber().getCountryCode()+""+phoneNumber.getNumber());

        if(!editMode)DummyData.addUser(p);

        Toast.makeText(getContext(), "user baru berhasil ditambahkan."+phoneNumber.getPhoneNumber(), Toast.LENGTH_SHORT).show();
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
            phoneNumber.setError("Masukkan Nomor handphone yang valid.");
            valid = false;
            Toast.makeText(getContext(), "Masukkan Nomor handphone yang valid.", Toast.LENGTH_SHORT).show();
        }else{
            phoneNumber.setError(null);
        }
        return valid;
    }


    private void retrieve_rooms() {
        String tag_string_req = "req_retrieve_rooms";
        String url = AppConfig.URL_RETRIEVE_ROOM_LIST;

        final HashMap params = new HashMap();

        HashMap headers = new HashMap();
        headers.put("Authorization", session.getToken());
        addRequest(tag_string_req, Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogUtil.logD(TAG, ">> retrieve_rooms Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    String message = ""+jObj.getString("message");
                    int status = jObj.getInt("status");
                    if(status == 200){
                        JSONArray data = jObj.getJSONArray("data");
                        if(data != null){
                            rooms.clear();
                            for (int i = 0; i < data.length(); i++) {
                                DataParser parser = new DataParser();
                                Room room = parser.parseRoomA(data.getJSONObject(i));
                                rooms.add(room);
                            }
                            roomAdapter.notifyDataSetChanged();
                            AppController.getInstance().rooms = rooms;
                        }
                    }else{
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    LogUtil.logE(TAG, ""+e.getMessage());
                }
                if(progressDialog.isShowing()) progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                LogUtil.logE(TAG, ""+e.getMessage());
                if(progressDialog.isShowing()) progressDialog.dismiss();
            }
        }, params, headers);
    }
}
