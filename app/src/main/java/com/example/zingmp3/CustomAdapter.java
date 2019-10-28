package com.example.zingmp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<DataMusic> implements View.OnClickListener{

    private ArrayList<DataMusic> dataSet;
    Context context;
    static int number = 0;

    private static class ViewHolder {
        TextView num_order;
        TextView name_song;
        TextView name_singer;
        ImageView three_dot;
    }

    public CustomAdapter(ArrayList<DataMusic> data, Context context) {
        super(context, R.layout.custom_item, data);
        this.dataSet = data;
        this.context = context;
    }

    @Override
    // Tag chinh la so thu tu cua phan tu trong listViewAdapter;
    public void onClick(View view) {
        int position = (Integer) view.getTag();
        Object object = getItem(position);
        DataMusic dataMusic = (DataMusic)object;

        switch(view.getId())
        {
            case R.id.three_dot:
                Toast.makeText(context, " + " + position + " + ", Toast.LENGTH_SHORT).show();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataMusic dataMusic = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_item, parent, false);
            viewHolder.num_order = convertView.findViewById(R.id.number_order);
            viewHolder.name_song = convertView.findViewById(R.id.name_song);
            viewHolder.name_singer = convertView.findViewById(R.id.name_singer);
            viewHolder.three_dot = convertView.findViewById(R.id.three_dot);

            result = convertView;
            convertView.setTag(viewHolder);
            Toast.makeText(this.context, dataMusic.getNumerical_order() + " + " + number++, Toast.LENGTH_SHORT).show();
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
            result = convertView;
        }

        viewHolder.num_order.setText(dataMusic.getNumerical_order());
        viewHolder.name_song.setText(dataMusic.getName_song());
        viewHolder.name_singer.setText(dataMusic.getName_singer());
        viewHolder.three_dot.setOnClickListener(this);
        viewHolder.three_dot.setTag(position);

        return convertView;
    }
}






















