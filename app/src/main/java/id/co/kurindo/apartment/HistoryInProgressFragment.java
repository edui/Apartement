package id.co.kurindo.apartment;

import id.co.kurindo.apartment.base.AppConfig;

/**
 * Created by dwim on 2/3/2017.
 */

public class HistoryInProgressFragment extends HistoryFragment {
    private static final String TAG = "HistoryInProgressFragment";

    @Override
    protected String getRetrieveHistoryUrl() {
        String url = AppConfig.URL_USER_DELATION_LIST;
        url = url.replace("{category}", "all/"+AppConfig.STATUS_PROGRESS.toLowerCase());
        return url;
    }
}
