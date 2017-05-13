package id.co.kurindo.apartment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lamudi.phonefield.PhoneInputLayout;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import id.co.kurindo.apartment.adapter.PersonAdapter;
import id.co.kurindo.apartment.base.BaseActivity;
import id.co.kurindo.apartment.base.BaseFragment;
import id.co.kurindo.apartment.model.User;
import id.co.kurindo.apartment.util.DummyData;
import id.co.kurindo.apartment.util.RecyclerItemClickListener;

/**
 * Created by aspire on 12/29/2016.
 */

public class AccountSettingFragment extends BaseFragment{
    @Bind(R.id.input_firstname)
    EditText inputFirstname;
    @Bind(R.id.input_lastname)
    EditText inputLastname;
    @Bind(R.id.input_email)
    EditText inputEmail;
    @Bind(R.id.input_phone)
    PhoneInputLayout phoneNumber;
    @Bind(R.id.tvAddFamilyProfile)
    TextView tvAddFamilyProfile;
    @Bind(R.id.tvAddBusinessProfile)
    TextView tvAddBusinessProfile;

    @Bind(R.id.ivAddFamilyProfile)
    ImageView ivAddFamilyProfile;
    @Bind(R.id.ivAddBusinessProfile)
    ImageView ivAddBusinessProfile;

    @Bind(R.id.layoutAddTenant)
    LinearLayout layoutAddTenant;

    @Bind(R.id.rvFamilyMembers)
    RecyclerView rvFamilyMembers;
    List<User> members = new ArrayList<>();
    PersonAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflateAndBind(inflater, container, R.layout.fragment_account_setting);
        members.addAll(DummyData.users);
        adapter = new PersonAdapter(getContext(), members);
        rvFamilyMembers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFamilyMembers.setAdapter(adapter);
        rvFamilyMembers.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        User u = members.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("user",u);
                        ((BaseActivity)getActivity()).showActivity(FamilyActivity.class, bundle);
                    }
                }
        ));

        User user = DummyData.user;
        inputFirstname.setText(user.getFirstname());
        inputLastname.setText(user.getLastname());
        inputEmail.setText(user.getEmail());
        phoneNumber.setDefaultCountry("ID");
        phoneNumber.setPhoneNumber(user.getPhone());
        //if(user.getPhone().startsWith("+")) phoneNumber.setNumber((user.getPhone().startsWith("+")? "":"+")+user.getPhone());
        layoutAddTenant.setVisibility(View.GONE);
        if(session.isOwner()){
            layoutAddTenant.setVisibility(View.VISIBLE);
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        members.clear(); members.addAll(DummyData.users);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tvAddFamilyProfile)
    public void onClick_tvAddFamilyProfile(){
        ((BaseActivity)getActivity()).showActivity(FamilyActivity.class);
    }
    @OnClick(R.id.ivAddFamilyProfile)
    public void onClick_ivAddFamilyProfile(){
        ((BaseActivity)getActivity()).showActivity(FamilyActivity.class);
    }
    @OnClick(R.id.tvAddBusinessProfile)
    public void onClick_tvAddBusinessProfile(){
        ((BaseActivity)getActivity()).showActivity(BusinessProfileActivity.class);
    }
    @OnClick(R.id.ivAddBusinessProfile)
    public void onClick_ivAddBusinessProfile(){
        ((BaseActivity)getActivity()).showActivity(BusinessProfileActivity.class);
    }
}
