
package id.co.kurindo.apartment.adapter;

/**
 * Created by DwiM on 11/9/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.co.kurindo.apartment.R;
import id.co.kurindo.apartment.helper.SessionManager;
import id.co.kurindo.apartment.model.History;
import id.co.kurindo.apartment.model.Room;


/**
 * Created by DwiM on 11/9/2016.
 */
public class RoomViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Room> data = new ArrayList<>();
    SessionManager session;
    private OnItemClickListener itemClickListener;
    public RoomViewAdapter(Context context, List<Room> data, OnItemClickListener itemClickListener) {
        this.context = context;
        this.data = data;
        this.session = new SessionManager(context);
        this.itemClickListener = itemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_room_view, parent, false);
        viewHolder = new MyItemHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vholder, final int position) {
        MyItemHolder holder= (MyItemHolder) vholder;
        Room model = data.get(position);
        /*
        Glide.with(context).load(model.getUrl())
                .thumbnail(0.5f)
                //.override(200,200)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((MyItemHolder) holder).mImg);
        */
        holder.mTextView.setText(model.getOwner().getFullname());
        holder.mTextViewTitle.setText(model.getRoomNumber());
        String roomStr = session.getRoom();
        String room = roomStr;
        if(roomStr != null && roomStr.contains("|")){
            room = roomStr.substring(0, roomStr.indexOf("|"));
        }
        if(model.getRoomNumber().equalsIgnoreCase(room)){
            holder.btnSetDefault.setBackgroundResource(R.color.colorAccent );
            holder.btnSetDefault.setText("Default");
        }else{
            holder.btnSetDefault.setBackgroundResource(R.color.grey);
            holder.btnSetDefault.setText("");
            holder.btnSetDefault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onSetDefaultButtonClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyItemHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        TextView mTextViewTitle;
        Button btnSetDefault;


        public MyItemHolder(View itemView) {
            super(itemView);

            mTextViewTitle = (TextView) itemView.findViewById(R.id.item_title);
            mTextView = (TextView) itemView.findViewById(R.id.item_text);
            btnSetDefault = (Button) itemView.findViewById(R.id.btnSetDefault);
        }

    }

    public interface OnItemClickListener {
        void onSetDefaultButtonClick(View view, int position);
    }
}