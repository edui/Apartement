package id.co.kurindo.apartment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import id.co.kurindo.apartment.adapter.ButtonAdapter;
import id.co.kurindo.apartment.base.AppConfig;
import id.co.kurindo.apartment.base.AppController;
import id.co.kurindo.apartment.base.BaseActivity;
import id.co.kurindo.apartment.base.BaseFragment;
import id.co.kurindo.apartment.helper.ProgressDialogCustom;
import id.co.kurindo.apartment.model.Issue;
import id.co.kurindo.apartment.model.Room;
import id.co.kurindo.apartment.util.DataParser;
import id.co.kurindo.apartment.util.DummyData;
import id.co.kurindo.apartment.util.LogUtil;
import id.co.kurindo.apartment.util.RecyclerItemClickListener;

/**
 * Created by aspire on 12/23/2016.
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";

    @Bind(R.id.list)
    RecyclerView rvList;
    ButtonAdapter mAdapter;
    ArrayList<Issue> data = new ArrayList<>();
    Context context;
    ProgressDialogCustom progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflateAndBind(inflater, container, R.layout.fragment_home);
        context = getContext();
        progressDialog = new ProgressDialogCustom(context);

        data.clear();
        data.addAll(DummyData.menus);
        rvList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvList.setHasFixedSize(true);
        mAdapter = new ButtonAdapter(getContext(), data);
        rvList.setAdapter(mAdapter);
        rvList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Issue model = data.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("issue", model);
                        //((MainActivity) getActivity()).showFragment(SimpleFormFragment.class, bundle);
                        ((MainActivity) getActivity()).showActivity(SimpleFormActivity.class, bundle);
                    }
                }
            )
        );

        retrieve_room_default();
        return v;
    }

    private void retrieve_room_default() {
        FragmentActivity activity = getActivity();
        if(activity != null){
            String tag_string_req = "req_retrieve_room";
            String url = AppConfig.URL_RETRIEVE_ROOM_DEFAULT;
            url = url.replace("{type}", "default");

            final HashMap params = new HashMap();
            params.put("type", "default");
            HashMap headers = new HashMap();
            headers.put("token", session.getToken());

            ((BaseActivity)activity).addRequest(tag_string_req, Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    LogUtil.logD(TAG, "Home > retrieve_room_default Response: " + response.toString());
                    try {
                        JSONObject jObj = new JSONObject(response);
                        String message = ""+jObj.getString("message");
                        int status = jObj.getInt("status");
                        if(status == 200){
                            JSONObject data = jObj.getJSONObject("data");
                            if(data != null){
                                DataParser parser = new DataParser();
                                Room room = parser.parseRoom(data);
                                AppController.getInstance().room = room;
                            }
                        }else{
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        LogUtil.logE(TAG, ""+e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError e) {
                    LogUtil.logE(TAG, ""+e.getMessage());
                }
            }, params, headers);
        }

    }
}
