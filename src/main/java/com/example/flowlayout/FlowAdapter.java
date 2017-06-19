package com.example.flowlayout;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yongzheng on 17-6-5.
 */

public class FlowAdapter extends BaseAdapter {

    private Context context;

    private List<String> arr;

    public FlowAdapter(Context context,List<String> data){
        this.arr = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item,null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.text);
        textView.setText(arr.get(position));
        if (position%2==0) {
            textView.setBackgroundColor(Color.WHITE);
//            if (position%4==0){
//            }else {
//                textView.setBackgroundColor(Color.RED);
//            }

        }else {
            textView.setBackgroundColor(Color.parseColor("#eeeeee"));
//            if ((position+1)%4==0){
//            }else {
//                textView.setBackgroundColor(Color.BLUE);
//            }
        }
        return convertView;
    }

}
