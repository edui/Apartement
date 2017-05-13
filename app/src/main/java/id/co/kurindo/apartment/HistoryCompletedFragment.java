package id.co.kurindo.apartment;



import id.co.kurindo.apartment.base.AppConfig;

/**
 * Created by dwim on 2/3/2017.
 */

public class HistoryCompletedFragment extends HistoryFragment {
    private static final String TAG = "HistoryCompletedFragment";

    @Override
    protected String getRetrieveHistoryUrl() {
        String url = AppConfig.URL_USER_DELATION_LIST;
        url = url.replace("{category}", "all/"+AppConfig.STATUS_CLOSED.toLowerCase());
        return url;
    }
}
