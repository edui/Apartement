package id.co.kurindo.apartment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import id.co.kurindo.apartment.adapter.RoomAdapter;
import id.co.kurindo.apartment.base.AppConfig;
import id.co.kurindo.apartment.base.AppController;
import id.co.kurindo.apartment.base.BaseActivity;
import id.co.kurindo.apartment.base.BaseFragment;
import id.co.kurindo.apartment.helper.ProgressDialogCustom;
import id.co.kurindo.apartment.helper.ViewHelper;
import id.co.kurindo.apartment.model.History;
import id.co.kurindo.apartment.model.Issue;
import id.co.kurindo.apartment.model.Pilihan;
import id.co.kurindo.apartment.model.Room;
import id.co.kurindo.apartment.model.User;
import id.co.kurindo.apartment.util.DataParser;
import id.co.kurindo.apartment.util.DummyData;
import id.co.kurindo.apartment.util.LogUtil;

/**
 * Created by aspire on 12/23/2016.
 */

public class SimpleFormFragment extends BaseFragment {
    private static final String TAG = "SimpleFormFragment";

    Issue issue;
    @Bind(R.id.rgPilihan)
    RadioGroup rgPilihan;

    @Bind(R.id.spinnerRoom)
    Spinner selectRoom;
    Room selectedRoom;

    @Bind(R.id.ivPageIcon)
    ImageView ivPageIcon;
    @Bind(R.id.tvPageTitle)
    TextView tvPageTitle;

    @Bind(R.id.input_comments)
    EditText inputComments;

    @Bind(R.id.submitBtn)
    AppCompatButton submitBtn;

    List<RadioButton> choices = new ArrayList<>();
    List<Room> rooms = new ArrayList<>();
    List<Pilihan> pilihans;
    String subject;
    RoomAdapter roomAdapter;
    Context context;
    ProgressDialogCustom progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        issue = bundle.getParcelable("issue");
        context = getContext();
        progressDialog = new ProgressDialogCustom(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflateAndBind(inflater, container, R.layout.fragment_simpleform);
        if(issue != null){
            ivPageIcon.setImageResource(issue.getDrawable());
            tvPageTitle.setText(issue.getName());
            pilihans = issue.getItems();
            if(pilihans == null || pilihans.size() == 0){
                pilihans = DummyData.MAP_MENUS.get(issue.getName()).getItems();
            }
            for(int i=0; i< pilihans.size(); i++)
            {
                Pilihan pl = pilihans.get(i);
                RadioButton rb=new RadioButton(getContext());
                rb.setText(pl.getCode() +". "+pl.getText());
                rb.setId(i);
                choices.add(rb);
                rgPilihan.addView(rb);
            }

            //rooms.addAll(DummyData.rooms);
            if(AppController.getInstance().rooms == null) retrieve_rooms();
            else rooms = AppController.getInstance().rooms;

            roomAdapter = new RoomAdapter(getContext(), rooms);
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
            if(AppController.getInstance().room != null){
                for (int i = 0; i < rooms.size(); i++) {
                    Room r = rooms.get(i);
                    if(r.getRoomNumber().equalsIgnoreCase(AppController.getInstance().room.getRoomNumber())){
                        selectRoom.setSelection(i);
                        break;
                    }
                }
            }
            rgPilihan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    Pilihan pil = pilihans.get(checkedId);
                    subject = pil.getText();
                }
            });
/*
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy h:m:s");
                    String comment = issue.getName()+" Report\n"+
                            "Jenis aduan :"+choices.get(rgPilihan.getCheckedRadioButtonId()).getText()+"\n"+
                            "Comments : "+inputComments.getText()+"\n"+
                            "Waktu Laporan : "+sdf.format(new Date());
                    History h = new History(sdf.format(new Date()), comment);
                    DummyData.addHistory(h);
                }
            });*/
        }
        return v;
    }

    public void onClick_SubmitBtnDummy(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy h:m:s");
        String room = (selectedRoom != null? selectedRoom.getRoomNumber() : "");
        String comment = issue.getName()+" Report\n"+
                "Room : "+room+"\n"+
                "Jenis aduan :"+choices.get(rgPilihan.getCheckedRadioButtonId()).getText()+"\n"+
                "Keluhan : "+inputComments.getText()+"\n"+
                "Waktu Laporan : "+sdf.format(new Date());
        //History h = new History(sdf.format(new Date()), comment);
        //DummyData.addHistory(h);
        Toast.makeText(getContext(), "Laporan sudah dikirimkan.", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @OnClick(R.id.submitBtn)
    public void onClick_SubmitBtn(){
        progressDialog.show();
        if (!validate()) {
            onFailed();
            return;
        }
        issue.setCategory(issue.getName());
        issue.setSubject( subject );
        issue.setComment(inputComments.getText().toString());
        issue.setRoom(AppController.getInstance().room);
        issue.setCreated(System.currentTimeMillis());

        submit_issue(issue);
    }

    private void submit_issue(Issue issue) {
        String tag_string_req = "req_submit_issue";
        String url = AppConfig.URL_USER_DELATION_SUBMIT;

        final GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting(); builder.serializeNulls();
        Gson gson = builder.create();

        final HashMap params = new HashMap();
        params.put("category", ""+issue.getCategory());
        params.put("comment", ""+issue.getComment());
        params.put("subject", ""+issue.getSubject());
        params.put("room", ""+issue.getRoom().getRoomNumber());
        params.put("roomObj", gson.toJson(issue.getRoom()));
        //LogUtil.logD(TAG, gson.toJson(params));
        HashMap headers = new HashMap();
        headers.put("Authorization", session.getToken());
        addRequest(tag_string_req, Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogUtil.logD(TAG, ">> submit_issue Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    String message = ""+jObj.getString("message");
                    int status = jObj.getInt("status");
                    if(status == 200){
                        JSONObject data = jObj.getJSONObject("data");
                        if(data != null){
                            DataParser parser = new DataParser();
                            Issue issue = parser.parseIssue(data);
                            //ViewHelper.getInstance().setIssue(issue);
                            ((BaseActivity) getActivity()).showActivity(HistoryActivity.class);
                        }
                        onSuccess();
                    }else{
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        onFailed();
                    }
                }catch (Exception e){
                    LogUtil.logE(TAG, ""+e.getMessage());
                    onFailed();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                LogUtil.logE(TAG, ""+e.getMessage());
                onFailed();
            }
        }, params, headers);

    }

    private void onSuccess() {
        submitBtn.setEnabled(true);
        progressDialog.dismiss();
        getActivity().finish();
    }

    private void onFailed() {
        submitBtn.setEnabled(true);
        progressDialog.dismiss();
    }

    private boolean validate() {
        boolean valid = true;
        if(AppController.getInstance().room == null){
            Toast.makeText(context, "Belum memilih Kamar / Apartment", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(inputComments.getText().toString().length() < 4){
            inputComments.setError("Inputkan keluhan anda.");
            valid = false;
        }else{
            inputComments.setError(null);
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
                    String message = "" + jObj.getString("message");
                    int status = jObj.getInt("status");
                    if (status == 200) {
                        JSONArray data = jObj.getJSONArray("data");
                        if (data != null) {
                            rooms.clear();
                            for (int i = 0; i < data.length(); i++) {
                                DataParser parser = new DataParser();
                                Room room = parser.parseRoomA(data.getJSONObject(i));
                                rooms.add(room);
                            }
                            roomAdapter.notifyDataSetChanged();
                            AppController.getInstance().rooms = rooms;
                        }
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.logE(TAG, "" + e.getMessage());
                }
                if (progressDialog.isShowing()) progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                LogUtil.logE(TAG, "" + e.getMessage());
                if (progressDialog.isShowing()) progressDialog.dismiss();
            }
        }, params, headers);
    }
}
