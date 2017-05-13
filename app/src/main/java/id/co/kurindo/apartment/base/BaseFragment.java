package id.co.kurindo.apartment.base;

import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import id.co.kurindo.apartment.R;
import id.co.kurindo.apartment.helper.SQLiteHandler;
import id.co.kurindo.apartment.helper.SessionManager;
import id.co.kurindo.apartment.util.LogUtil;

import static id.co.kurindo.apartment.util.LogUtil.makeLogTag;

/**
 * The base class for all fragment classes.
 *
 */
public class BaseFragment extends Fragment {

    private static final String TAG = makeLogTag(BaseFragment.class);
    protected SQLiteHandler db;
    protected SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new SQLiteHandler(getActivity());
        session = new SessionManager(getActivity());
    }

    /**
     * Inflates the layout and binds the view via ButterKnife.
     * @param inflater the inflater
     * @param container the layout container
     * @param layout the layout resource
     * @return the inflated view
     */
    public View inflateAndBind(LayoutInflater inflater, ViewGroup container, int layout) {
        View view = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, view);

        LogUtil.logD(TAG, ">>> view inflated");
        return view;
    }

    protected void showErrorDialog(String title, String message){
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(getContext());

        // set title
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        // create alert dialog
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    protected void showConfirmationDialog(String title, String message, DialogInterface.OnClickListener YesListener, DialogInterface.OnClickListener NoListener){
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(getContext());

        // set title
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, YesListener)
                .setNegativeButton(R.string.no, NoListener);

        // create alert dialog
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    public void addRequest(final String tag_string_req, int method, String url, Response.Listener responseListener, Response.ErrorListener errorListener, final Map<String, String> params, final Map<String, String> headers){
        final StringRequest strReq = new StringRequest(method,url, responseListener, errorListener){
            protected Map<String, String> getParams() throws AuthFailureError {
                if(params == null) return super.getParams();
                return params;
            }
            public Map<String, String> getHeaders() throws AuthFailureError{
                if(headers == null) return super.getHeaders();
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    public Map<String,String> getKurindoHeaders() {
        Map<String, String> headers = null;
        String api = db.getUserApi();
        if(api != null && !api.isEmpty()){
            headers = new HashMap<>();
            headers.put("Api", api);
        }
        return headers;
    }

}
