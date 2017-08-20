package com.lupy.addressselectorlibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lupy.addressselectorlibrary.bean.Area;

import java.util.List;

/**
 * Created by Lupy
 * on 2017/8/20.
 */

public class AreaAdapter extends BaseAdapter {
    private List<Area> areas;
    private Context context;
    private LayoutInflater inflater;

    public AreaAdapter(List<Area> areas,Context context) {
        this.areas = areas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return areas.size();
    }

    @Override
    public Object getItem(int position) {
        return areas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.area_item,null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.itemText.setText(areas.get(position).name);
        return convertView;
    }

    class ViewHolder{
        public TextView itemText;

        public ViewHolder(View view) {
            itemText = (TextView) view.findViewById(R.id.item_text);
        }
    }
}
