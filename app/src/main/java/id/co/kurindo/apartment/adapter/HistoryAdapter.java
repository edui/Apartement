
package id.co.kurindo.apartment.adapter;

/**
 * Created by DwiM on 11/9/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.co.kurindo.apartment.R;
import id.co.kurindo.apartment.base.AppConfig;
import id.co.kurindo.apartment.model.History;
import id.co.kurindo.apartment.util.DummyData;


/**
 * Created by DwiM on 11/9/2016.
 */
public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<History> data = new ArrayList<>();
    boolean showButton;
    OnItemClickListener onItemClickListener;

    public HistoryAdapter(Context context, List<History> data, OnItemClickListener itemClickListener) {
        this(context,data, false,itemClickListener);
    }
    public HistoryAdapter(Context context, List<History> data, boolean showButton, OnItemClickListener itemClickListener) {
        this.context = context;
        this.data = data;
        this.showButton=showButton;
        this.onItemClickListener = itemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_history, parent, false);
        viewHolder = new MyItemHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vholder, final int position) {
        History model = data.get(position);
         MyItemHolder holder = (MyItemHolder) vholder;
        holder.buttonLayout.setVisibility(showButton?View.VISIBLE:View.GONE);
        /*
        Glide.with(context).load(model.getUrl())
                .thumbnail(0.5f)
                //.override(200,200)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mImg);
        */
        holder.mTextView.setText(model.getIssue().toStringSummary());
        holder.mTextViewTitle.setText(model.getDate());
        if(AppConfig.isNew(model.getStatus())){
            holder.assign.setImageResource(R.drawable.ic_input_black_18dp);
            holder.assign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onAssignButtonClick(v, position);
                }
            });
            holder.ip.setImageResource(R.drawable.ic_hourglass_empty_black_18dp);
            holder.completed.setImageResource(R.drawable.ic_assignment_turned_in_white_18dp);
        }else
        if(AppConfig.isInProgress(model.getStatus())){
            holder.assign.setImageResource(R.drawable.ic_input_black_18dp);
            holder.ip.setImageResource(R.drawable.ic_hourglass_full_black_18dp);
            holder.completed.setImageResource(R.drawable.ic_assignment_turned_in_white_18dp);
            holder.completed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onCompletedButtonClick(v, position);
                }
            });
        }else
        if(AppConfig.isClosed(model.getStatus())){
            holder.assign.setImageResource(R.drawable.ic_input_black_18dp);
            holder.ip.setImageResource(R.drawable.ic_hourglass_full_black_18dp);
            holder.completed.setImageResource(R.drawable.ic_assignment_turned_in_black_18dp);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyItemHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        TextView mTextViewTitle;
        ImageView assign;
        ImageView ip;
        ImageView completed;
        LinearLayout buttonLayout;


        public MyItemHolder(View itemView) {
            super(itemView);

            mTextViewTitle = (TextView) itemView.findViewById(R.id.item_title);
            mTextView = (TextView) itemView.findViewById(R.id.item_text);
            assign = (ImageView) itemView.findViewById(R.id.assign);
            ip = (ImageView) itemView.findViewById(R.id.ip);
            completed= (ImageView) itemView.findViewById(R.id.completed);
            buttonLayout= (LinearLayout) itemView.findViewById(R.id.buttonLayout);

        }

    }


    public interface OnItemClickListener {
        void onAssignButtonClick(View view, int position);
        void onIpButtonClick(View view, int position);
        void onCompletedButtonClick(View view, int position);
    }
}