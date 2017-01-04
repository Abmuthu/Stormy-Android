package com.example.abmuthu.stormy.controller;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.abmuthu.stormy.R;
import com.example.abmuthu.stormy.adapter.DayAdapter;
import com.example.abmuthu.stormy.model.Day;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyWeatherActivity extends ListActivity {

    private Day[] mDays;
    @BindView(R.id.locationTextView) TextView mLocationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_weather);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_WEATHER);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        DayAdapter adapter = new DayAdapter(this, mDays);
        setListAdapter(adapter);

        mLocationTextView.setText(mDays[0].getTimeZone());

    }
}
