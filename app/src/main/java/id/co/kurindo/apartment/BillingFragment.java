package id.co.kurindo.apartment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import id.co.kurindo.apartment.adapter.BillingAdapter;
import id.co.kurindo.apartment.base.BaseFragment;
import id.co.kurindo.apartment.model.Billing;
import id.co.kurindo.apartment.util.DummyData;

/**
 * Created by aspire on 12/23/2016.
 */

public class BillingFragment extends BaseFragment {
    @Bind(R.id.list)
    RecyclerView rvList;
    BillingAdapter rAdapter;
    List<Billing> ada = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflateAndBind(inflater, container, R.layout.fragment_billing);
        ada.clear();
        ada.addAll(DummyData.billings);
        rAdapter = new BillingAdapter(getContext(), ada);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(rAdapter);
        return v;
    }
}
