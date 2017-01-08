package id.co.kurindo.apartment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import id.co.kurindo.apartment.adapter.ButtonAdapter;
import id.co.kurindo.apartment.base.BaseFragment;
import id.co.kurindo.apartment.model.ImageModel;
import id.co.kurindo.apartment.model.Product;
import id.co.kurindo.apartment.util.DummyData;
import id.co.kurindo.apartment.util.RecyclerItemClickListener;

/**
 * Created by aspire on 12/23/2016.
 */

public class HomeFragment extends BaseFragment {
    @Bind(R.id.list)
    RecyclerView rvList;
    ButtonAdapter mAdapter;
    ArrayList<Product> data = new ArrayList<>();

    @Bind(R.id.tvAccount)
    TextView tvAccount;
    @Bind(R.id.tvHistory)
    TextView tvHistory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflateAndBind(inflater, container, R.layout.fragment_home);
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
                        Product model = data.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("product", model);
                        //((MainActivity) getActivity()).showFragment(SimpleFormFragment.class, bundle);
                        ((MainActivity) getActivity()).showActivity(SimpleFormActivity.class, bundle);
                    }
                }
            )
        );

        tvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showActivity(AccountSettingActivity.class);
            }
        });

        tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showActivity(HistoryActivity.class);
            }
        });
        return v;
    }
}
