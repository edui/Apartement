package id.co.kurindo.apartment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import id.co.kurindo.apartment.adapter.RoomAdapter;
import id.co.kurindo.apartment.base.BaseFragment;
import id.co.kurindo.apartment.model.History;
import id.co.kurindo.apartment.model.Pilihan;
import id.co.kurindo.apartment.model.Product;
import id.co.kurindo.apartment.model.Room;
import id.co.kurindo.apartment.util.DummyData;

/**
 * Created by aspire on 12/23/2016.
 */

public class SimpleFormFragment extends BaseFragment {
    Product product;
    @Bind(R.id.rgPilihan)
    RadioGroup rgPilihan;

    @Bind(R.id.spinnerRoom)
    Spinner selectRoom;
    Room selectedRoom;

    @Bind(R.id.ivPageIcon)
    ImageView ivPageIcon;
    @Bind(R.id.tvPageTitle)
    TextView tvPageTitle;

    @Bind(R.id.input_comments)
    EditText inputComments;

    @Bind(R.id.submitBtn)
    AppCompatButton submitBtn;

    List<RadioButton> choices = new ArrayList<>();
    List<Room> rooms = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        product = bundle.getParcelable("product");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflateAndBind(inflater, container, R.layout.fragment_simpleform);
        if(product != null){
            ivPageIcon.setImageResource(product.getDrawable());
            tvPageTitle.setText(product.getName());
            List<Pilihan> list = product.getItems();
            for(int i=0; i< list.size(); i++)
            {
                Pilihan pl = list.get(i);
                RadioButton rb=new RadioButton(getContext());
                rb.setText(pl.getCode() +". "+pl.getText());
                rb.setId(i);
                choices.add(rb);
                rgPilihan.addView(rb);
            }

            rooms.addAll(DummyData.rooms);
            RoomAdapter roomAdapter = new RoomAdapter(getContext(), rooms);
            selectRoom.setAdapter(roomAdapter);
            selectRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedRoom = (Room) parent.getItemAtPosition(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            /*
            rgPilihan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    //Toast.makeText(getContext(), "checkedID: "+checkedId, Toast.LENGTH_SHORT).show();
                }
            });*/
/*
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy h:m:s");
                    String comment = product.getName()+" Report\n"+
                            "Jenis aduan :"+choices.get(rgPilihan.getCheckedRadioButtonId()).getText()+"\n"+
                            "Comments : "+inputComments.getText()+"\n"+
                            "Waktu Laporan : "+sdf.format(new Date());
                    History h = new History(sdf.format(new Date()), comment);
                    DummyData.addHistory(h);
                }
            });*/
        }
        return v;
    }

    @OnClick(R.id.submitBtn)
    public void onClick_SubmitBtn(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy h:m:s");
        String room = (selectedRoom != null? selectedRoom.getRoomNumber() : "");
        String comment = product.getName()+" Report\n"+
                "Room : "+room+"\n"+
                "Jenis aduan :"+choices.get(rgPilihan.getCheckedRadioButtonId()).getText()+"\n"+
                "Keluhan : "+inputComments.getText()+"\n"+
                "Waktu Laporan : "+sdf.format(new Date());
        History h = new History(sdf.format(new Date()), comment);
        DummyData.addHistory(h);
        Toast.makeText(getContext(), "Laporan sudah dikirimkan.", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }
}
