package com.example.abmuthu.stormy.controller;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abmuthu.stormy.R;
import com.example.abmuthu.stormy.adapter.DayAdapter;
import com.example.abmuthu.stormy.model.Day;

import java.util.Arrays;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyWeatherActivity extends AppCompatActivity {

    private Day[] mDays;
    @BindView(R.id.locationTextView) TextView mLocationTextView;
    @BindView(R.id.emptyTextView) TextView mEmptyTextView;
    @BindView(R.id.listView) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_weather);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_WEATHER);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        DayAdapter adapter = new DayAdapter(this, mDays);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyTextView);
        mLocationTextView.setText(mDays[0].getTimeZone());

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String dayOfWeek = mDays[i].getDayOfTheWeek();
                String temperature = mDays[i].getMaxTemp() + "";
                String conditions = mDays[i].getSummary();

                String finalString = String.format("On %s the temperature will be %s and it will be %s",
                        dayOfWeek,
                        temperature,
                        conditions);

                Toast.makeText(DailyWeatherActivity.this, finalString, Toast.LENGTH_SHORT).show();
            }
        });


    }

//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//
//        String dayOfWeek = mDays[position].getDayOfTheWeek();
//        String temperature = mDays[position].getMaxTemp() + "";
//        String conditions = mDays[position].getSummary();
//
//        String finalString = String.format("On %s the temperature will be %s and it will be %s",
//                                    dayOfWeek,
//                                    temperature,
//                                    conditions);
//
//        Toast.makeText(this, finalString, Toast.LENGTH_SHORT).show();
//
//    }
}
