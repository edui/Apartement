package id.co.kurindo.apartment;


import java.util.List;

import id.co.kurindo.apartment.model.History;
import id.co.kurindo.apartment.util.DummyData;

/**
 * Created by dwim on 2/3/2017.
 */

public class HistoryInProgressJobFragment extends HistoryFragment {
    @Override
    public List<History> getData() {
        return DummyData.iphistories;
    }
}
