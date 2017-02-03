package id.co.kurindo.apartment;

import java.util.ArrayList;
import java.util.List;

import id.co.kurindo.apartment.base.BaseFragment;
import id.co.kurindo.apartment.model.History;
import id.co.kurindo.apartment.util.DummyData;

/**
 * Created by dwim on 2/3/2017.
 */

public class HistoryInProgressFragment extends HistoryFragment {
    @Override
    public List<History> getData() {
        List<History> data = DummyData.newhistories;
        data.addAll(DummyData.iphistories);
        return data;
    }
}
