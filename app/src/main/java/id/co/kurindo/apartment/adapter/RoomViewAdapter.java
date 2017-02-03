
package id.co.kurindo.apartment.adapter;

/**
 * Created by DwiM on 11/9/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.co.kurindo.apartment.R;
import id.co.kurindo.apartment.model.History;
import id.co.kurindo.apartment.model.Room;


/**
 * Created by DwiM on 11/9/2016.
 */
public class RoomViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Room> data = new ArrayList<>();

    public RoomViewAdapter(Context context, List<Room> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_room, parent, false);
        viewHolder = new MyItemHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Room model = data.get(position);
        /*
        Glide.with(context).load(model.getUrl())
                .thumbnail(0.5f)
                //.override(200,200)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((MyItemHolder) holder).mImg);
        */
        ((MyItemHolder) holder).mTextView.setText(model.getOwner().getFullname());
        ((MyItemHolder) holder).mTextViewTitle.setText(model.getRoomNumber());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyItemHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        TextView mTextViewTitle;


        public MyItemHolder(View itemView) {
            super(itemView);

            mTextViewTitle = (TextView) itemView.findViewById(R.id.item_title);
            mTextView = (TextView) itemView.findViewById(R.id.item_text);
        }

    }


}