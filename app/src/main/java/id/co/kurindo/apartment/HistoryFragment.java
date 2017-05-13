package id.co.kurindo.apartment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import id.co.kurindo.apartment.base.AppConfig;
import id.co.kurindo.apartment.base.BaseFragment;
import id.co.kurindo.apartment.helper.ProgressDialogCustom;
import id.co.kurindo.apartment.model.History;
import id.co.kurindo.apartment.model.Issue;
import id.co.kurindo.apartment.util.DataParser;
import id.co.kurindo.apartment.util.LogUtil;

/**
 * Created by aspire on 12/23/2016.
 */

public abstract class HistoryFragment extends BaseFragment {
    private static final String TAG = "HistoryFragment";

    @Bind(R.id.btnRefresh)
    Button btnRefresh;

    @Bind(R.id.list)
    RecyclerView rvList;
    HistoryAdapter rAdapter;
    protected List<History> ada = new ArrayList<>();
    ProgressDialogCustom progressDialog;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflateAndBind(inflater, container, R.layout.fragment_history);
        context = getContext();
        progressDialog = new ProgressDialogCustom(context);
        getData();
        //ada.addAll(getData());
        rAdapter = new HistoryAdapter(context, ada, true, getOnClickListener());
        rvList.setLayoutManager(new LinearLayoutManager(context));
        rvList.setAdapter(rAdapter);
        return v;
    }
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message mesg) {
            throw new RuntimeException();
        }
    };

    private HistoryAdapter.OnItemClickListener getOnClickListener() {
        return new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onAssignButtonClick(View view, final int position) {
                if(session.isTenant()) return;

                final History model = ada.get(position);

                DialogInterface.OnClickListener YesClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    update_status(model, position, AppConfig.STATUS_PROGRESS, model.getStatus(), handler);
                                }
                            });
                        }
                    }
                };
                showConfirmationDialog("Confirm Status","Anda Yakin akan merubah status order '"+model.getIssue().getCategory()+"' menjadi '"+ AppConfig.STATUS_PROGRESS +"' ?", YesClickListener, null);

                // loop till a runtime exception is triggered.
                try { Looper.loop(); }
                catch(RuntimeException e2) {}
            }

            @Override
            public void onIpButtonClick(View view, final int position) {
                if(session.isTenant()) return;

                final History model = ada.get(position);

                DialogInterface.OnClickListener YesClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    update_status(model, position, AppConfig.STATUS_CLOSED, model.getStatus(), handler);
                                }
                            });
                        }
                    }
                };
                showConfirmationDialog("Confirm Status","Anda Yakin akan merubah status order '"+model.getIssue().getCategory()+"' menjadi '"+ AppConfig.STATUS_CLOSED+"' ?", YesClickListener, null);

                // loop till a runtime exception is triggered.
                try { Looper.loop(); }
                catch(RuntimeException e2) {}
            }

            @Override
            public void onCompletedButtonClick(View view, final int position) {
                if(session.isTenant()) return;

                final History model = ada.get(position);

                DialogInterface.OnClickListener YesClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    update_status(model, position, AppConfig.STATUS_CLOSED, model.getStatus(), handler);
                                }
                            });
                        }
                    }
                };
                showConfirmationDialog("Confirm Status","Anda Yakin akan merubah status order '"+model.getIssue().getCategory()+"' menjadi '"+ AppConfig.STATUS_CLOSED+"' ?", YesClickListener, null);

                // loop till a runtime exception is triggered.
                try { Looper.loop(); }
                catch(RuntimeException e2) {}
            }
        };
    }

    private void update_status(History model, final int position, final String statusBaru, String statusLama, final Handler handler) {
        progressDialog.show();

        String tag_string_req = "req_update_issue";
        String url = AppConfig.URL_USER_DELATION_UPDATE;
        url = url.replace("{id}", model.getIssue().getId());

        final HashMap params = new HashMap();
        params.put("status", statusBaru);
        params.put("filter", statusLama);

        HashMap headers = new HashMap();
        headers.put("Authorization", session.getToken());
        LogUtil.logD(TAG, "Token: "+session.getToken());

        addRequest(tag_string_req, Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogUtil.logD(TAG, ">> retrieve_histories Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    String message = ""+jObj.getString("message");
                    int status = jObj.getInt("status");
                    if(status == 200){
                        JSONObject data = jObj.getJSONObject("data");
                        if(data != null){
                            ada.remove(position);
                            //History m = ada.get(position);
                            //m.getIssue().setStatus(statusBaru);
                            rAdapter.notifyDataSetChanged();
                            /*DataParser parser = new DataParser();
                            Issue issue = parser.parseIssue(data);
                            if(AppConfig.isClosed(issue.getStatus())){
                                History history = new History(AppConfig.getSdf().format(new Date(issue.getCreated())), issue);
                                ada.add(history);
                                rAdapter.notifyDataSetChanged();
                            }
                            */
                        }
                    }else{
                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    LogUtil.logE(TAG, ""+e.getMessage());
                }
                progressDialog.dismiss();
                handler.handleMessage(null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                LogUtil.logE(TAG, ""+e.getMessage());
                progressDialog.dismiss();
                handler.handleMessage(null);
            }
        }, params, headers);
    }

    @OnClick(R.id.btnRefresh)
    public void OnBtnRefresh_Click(){
        progressDialog.show();
        getData();
    }
    public List<History> getData(){
        //ada.clear();
        //ada.addAll(DummyData.histories);
        retrieve_histories();
        return ada;
    }

    protected abstract String getRetrieveHistoryUrl();

    private void retrieve_histories() {
        String tag_string_req = "req_retrieve_histories_inprogress";
        String url = getRetrieveHistoryUrl();

        final HashMap params = new HashMap();

        HashMap headers = new HashMap();
        headers.put("Authorization", session.getToken());
        addRequest(tag_string_req, Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogUtil.logD(TAG, ">> retrieve_histories Response: " + response.toString());
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
                                Issue issue = parser.parseIssue(data.getJSONObject(i));
                                //if(AppConfig.isInProgress(issue.getStatus())){
                                    History history = new History(AppConfig.getSdf().format(new Date(issue.getCreated())), issue);
                                    ada.add(history);
                                    rAdapter.notifyDataSetChanged();
                                //}
                            }
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
