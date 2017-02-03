package id.co.kurindo.apartment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import id.co.kurindo.apartment.adapter.HistoryAdapter;
import id.co.kurindo.apartment.adapter.RoomAdapter;
import id.co.kurindo.apartment.adapter.RoomViewAdapter;
import id.co.kurindo.apartment.base.BaseFragment;
import id.co.kurindo.apartment.model.History;
import id.co.kurindo.apartment.model.Room;
import id.co.kurindo.apartment.util.DummyData;

/**
 * Created by aspire on 12/23/2016.
 */

public class RoomFragment extends BaseFragment {
    @Bind(R.id.list)
    RecyclerView rvList;
    RoomViewAdapter rAdapter;
    List<Room> ada = new ArrayList<>();
    @Bind(R.id.pageTitle)
    TextView pageTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflateAndBind(inflater, container, R.layout.fragment_history);

        pageTitle.setText("My Rooms");
        ada.addAll(DummyData.rooms);
        rAdapter = new RoomViewAdapter(getContext(), ada);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(rAdapter);
        return v;
    }
}
