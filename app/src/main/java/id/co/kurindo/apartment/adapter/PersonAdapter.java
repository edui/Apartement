package id.co.kurindo.apartment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.co.kurindo.apartment.R;
import id.co.kurindo.apartment.model.Person;
import id.co.kurindo.apartment.model.User;

/**
 * Created by dwim on 1/8/2017.
 */

public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    Context context;
    List<User> data = new ArrayList<>();

    public PersonAdapter(Context context, List<User> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_person, parent, false);
        viewHolder = new MyItemHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Person model = data.get(position);
        ((MyItemHolder) holder).mTextViewTitle.setText(model.getFirstname() + " "+model.getLastname());
        ((MyItemHolder) holder).mTextView.setText(model.getEmail() );
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class MyItemHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile;
        TextView mTextViewTitle;
        TextView mTextView;


        public MyItemHolder(View itemView) {
            super(itemView);

            ivProfile = (ImageView) itemView.findViewById(R.id.item_image);
            mTextViewTitle = (TextView) itemView.findViewById(R.id.item_title);
            mTextView = (TextView) itemView.findViewById(R.id.item_text);
        }

    }
}
