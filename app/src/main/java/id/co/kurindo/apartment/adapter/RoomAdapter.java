package id.co.kurindo.apartment.adapter;

/**
 * Created by DwiM on 11/9/2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.co.kurindo.apartment.R;
import id.co.kurindo.apartment.model.Room;

/**
 * Created by DwiM on 11/9/2016.
 */
public class RoomAdapter extends BaseAdapter {

    Context context;
    List<Room> data = new ArrayList<>();
    private String[] bgColors;

    public RoomAdapter(Context context, List<Room> data) {
        this.context = context;
        this.data = data;
        bgColors = context.getResources().getStringArray(R.array.list_bg);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_room, parent, false);
        TextView code = (TextView) v.findViewById(R.id.item_title);
        TextView text = (TextView) v.findViewById(R.id.item_text);

        code.setText(String.valueOf(data.get(position).getRoomNumber()));
        text.setText(data.get(position).getOwner().getFullname());

        String color = bgColors[position % bgColors.length];
        code.setBackgroundColor(Color.parseColor(color));
        return v;
    }
}