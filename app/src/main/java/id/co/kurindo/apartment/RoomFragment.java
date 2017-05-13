package id.co.kurindo.apartment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import id.co.kurindo.apartment.adapter.HistoryAdapter;
import id.co.kurindo.apartment.adapter.RoomAdapter;
import id.co.kurindo.apartment.adapter.RoomViewAdapter;
import id.co.kurindo.apartment.base.AppConfig;
import id.co.kurindo.apartment.base.AppController;
import id.co.kurindo.apartment.base.BaseFragment;
import id.co.kurindo.apartment.model.History;
import id.co.kurindo.apartment.model.Issue;
import id.co.kurindo.apartment.model.Room;
import id.co.kurindo.apartment.util.DataParser;
import id.co.kurindo.apartment.util.DummyData;
import id.co.kurindo.apartment.util.LogUtil;

/**
 * Created by aspire on 12/23/2016.
 */

public class RoomFragment extends BaseFragment {
    private static final String TAG = "RoomFragment";

    @Bind(R.id.list)
    RecyclerView rvList;
    RoomViewAdapter rAdapter;
    List<Room> ada = new ArrayList<>();
    @Bind(R.id.pageTitle)
    TextView pageTitle;

    Context context;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflateAndBind(inflater, container, R.layout.fragment_history);
        context = getContext();
        progressDialog = new ProgressDialog(context);

        pageTitle.setText("My Rooms");
        //ada.addAll(DummyData.rooms);
        getData();
        rAdapter = new RoomViewAdapter(getContext(), ada, getItemClickListener());
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(rAdapter);
        return v;
    }

    @OnClick(R.id.btnRefresh)
    public void OnBtnRefresh_Click(){
        progressDialog.show();
        getData();
    }
    private void getData() {
        retrieve_rooms();
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
                            ada.clear();
                            for (int i = 0; i < data.length(); i++) {
                                DataParser parser = new DataParser();
                                Room room = parser.parseRoomA(data.getJSONObject(i));
                                ada.add(room);
                            }
                            rAdapter.notifyDataSetChanged();
                            AppController.getInstance().rooms = ada;
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

    private RoomViewAdapter.OnItemClickListener getItemClickListener() {
        return new RoomViewAdapter.OnItemClickListener() {
            @Override
            public void onSetDefaultButtonClick(View view, int position) {
                Room room =ada.get(position);
                session.setRoom(room.toStoreString());
                AppController.getInstance().room = room;
                rAdapter.notifyDataSetChanged();
                Intent intent = new Intent(Room.ROOM_CHANGED);
                intent.putExtra("room", room);
                getActivity().sendBroadcast(intent);
            }
        };
    }
}
