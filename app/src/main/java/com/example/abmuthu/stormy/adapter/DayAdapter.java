package com.example.abmuthu.stormy.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abmuthu.stormy.R;
import com.example.abmuthu.stormy.model.Day;

/**
 * Created by abmuthu on 1/4/17.
 */

public class DayAdapter extends BaseAdapter {

    private Context mContext;
    private Day[] mDays;

    public DayAdapter(Context context, Day[] days) {
        mContext = context;
        mDays = days;
    }

    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int i) {
        return mDays[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null);
            holder = new ViewHolder();
            holder.dayTextView = (TextView) view.findViewById(R.id.dayTextView);
            holder.iconImageView = (ImageView) view.findViewById(R.id.iconImageView);
            holder.tempTextView = (TextView) view.findViewById(R.id.tempTextView);
            holder.tempImageView = (ImageView) view.findViewById(R.id.tempImageView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }
        Day day = mDays[i];
        holder.iconImageView.setImageResource(day.getIconID());
        holder.tempTextView.setText(day.getMaxTemp() + "");
        holder.dayTextView.setText(day.getDayOfTheWeek());
        if(i == 0) {
            holder.dayTextView.setText("Today");
        } else {
            holder.dayTextView.setText(day.getDayOfTheWeek());
        }
        holder.tempImageView.setImageResource(R.drawable.bg_temperature);
        return view;

    }

    public static class ViewHolder {
        public ImageView iconImageView;
        public TextView dayTextView;
        public TextView tempTextView;
        public ImageView tempImageView;

    }
}
