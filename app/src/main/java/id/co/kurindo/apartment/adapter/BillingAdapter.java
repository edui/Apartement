
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
import id.co.kurindo.apartment.model.Billing;


/**
 * Created by DwiM on 11/9/2016.
 */
public class BillingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Billing> data = new ArrayList<>();

    public BillingAdapter(Context context, List<Billing> data) {
        this.context = context;
        this.data = data;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Billing model = data.get(position);

        ((MyItemHolder) holder).mTextView.setText(model.toString());
        ((MyItemHolder) holder).mTextViewTitle.setText(model.getMonth()+" "+model.getYear());
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