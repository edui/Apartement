package id.co.kurindo.apartment;


import id.co.kurindo.apartment.base.AppConfig;

/**
 * Created by dwim on 2/3/2017.
 */

public class HistoryNewJobFragment extends HistoryFragment {
    private static final String TAG = "HistoryNewJobFragment";

    @Override
    protected String getRetrieveHistoryUrl() {
        String url = AppConfig.URL_USER_DELATION_LIST;
        url = url.replace("{category}", "all/"+AppConfig.STATUS_REVIEW.toLowerCase());
        return url;
    }

}
