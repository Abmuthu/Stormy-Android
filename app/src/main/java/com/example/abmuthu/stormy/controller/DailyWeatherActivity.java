package com.example.abmuthu.stormy.controller;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.abmuthu.stormy.R;
import com.example.abmuthu.stormy.adapter.DayAdapter;
import com.example.abmuthu.stormy.model.Day;

public class DailyWeatherActivity extends ListActivity {

    private Day[] mDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_weather);
        Intent intent = getIntent();
        intent.getParcelableArrayExtra(MainActivity.DAILY_WEATHER);
        DayAdapter adapter = new DayAdapter(this, mDays);
        setListAdapter(adapter);

    }
}
