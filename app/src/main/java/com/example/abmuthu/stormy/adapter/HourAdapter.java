package com.example.abmuthu.stormy.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.StringDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abmuthu.stormy.R;
import com.example.abmuthu.stormy.model.Hour;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abmuthu on 1/4/17.
 */

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {

    private Hour[] mHours;
    private Context mContext;

    public HourAdapter(Context context, Hour[] hours) {
        mHours = hours;
        mContext = context;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.hourly_list_item, parent, false);
        HourViewHolder viewHolder = new HourViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bindHourToView(mHours[position]);
    }

    @Override
    public int getItemCount() {
        return mHours.length;
    }

    public class HourViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        @BindView(R.id.timeTextView) TextView mTimeLabel;
        @BindView(R.id.summaryTextView) TextView mSummary;
        @BindView(R.id.iconImageView) ImageView mIconImageView;
        @BindView(R.id.tempTextView) TextView mTemp;

        public HourViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        private void bindHourToView(Hour hour) {
            mTimeLabel.setText(hour.getOnlyHour());
            mSummary.setText(hour.getSummary());
            mIconImageView.setImageResource(hour.getIconID());
            mTemp.setText(hour.getTemperature() + "");
        }

        @Override
        public void onClick(View view) {
            String time = mTimeLabel.getText().toString();
            String summary = mSummary.getText().toString();
            String temp = mTemp.getText().toString();

            String finalString = String.format("At %s, the temperature will be %s and it will %s",
                                    time,
                                    temp,
                                    summary);
            Toast.makeText(mContext, finalString, Toast.LENGTH_SHORT).show();
        }
    }
}
