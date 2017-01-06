package com.example.abmuthu.stormy.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abmuthu.stormy.model.Current;
import com.example.abmuthu.stormy.model.Day;
import com.example.abmuthu.stormy.model.Forecast;
import com.example.abmuthu.stormy.model.Hour;
import com.example.abmuthu.stormy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_WEATHER = "DAILY_WEATHER";
    public static final String HOURLY_WEATHER = "HOURLY_WEATHER";
    private String mErrorMessage = null;

    private Forecast mForecast = null;

    @BindView(R.id.iconImageView) ImageView mIconImageView;
    @BindView(R.id.locationTextView) TextView mLocationTextView;
    @BindView(R.id.timeTextView) TextView mTimeTextView;
    @BindView(R.id.mainTextView) TextView mMainTextView;
    @BindView(R.id.humidityDataTextView) TextView mHumidityDataTextView;
    @BindView(R.id.precipitationDataTextView) TextView mPrecipitationDataTextView;
    @BindView(R.id.summary) TextView mSummary;
    @BindView(R.id.refreshImageView) ImageView mRefreshImageView;
    @BindView(R.id.refreshProgressBar) ProgressBar mRefreshProgressBar;
    @BindView(R.id.dailyButton) Button mDailyButton;
    @BindView(R.id.hourlyButton) Button mHourlyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRefreshProgressBar.setVisibility(View.INVISIBLE);
        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getForecast();
            }
        });
        mDailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DailyWeatherActivity.class);
                intent.putExtra(DAILY_WEATHER, mForecast.getDailyWeather());
                startActivity(intent);

            }
        });
        mHourlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HourlyWeatherActivity.class);
                intent.putExtra(HOURLY_WEATHER, mForecast.getHourlyWeather());
                startActivity(intent);
            }
        });
        getForecast();
    }

    private void getForecast() {
        String apiKey = "5df33850f2b800a4e706f7bfe967ae2b";
        double latitude = 37.8267;
        double longitude = -122.4233;
        String apiURL = "https://api.darksky.net/forecast/" + apiKey + "/" + latitude + "," + longitude;
        if(isNetworkAvailable()) {
            showProgressBar();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiURL)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mForecast = getOverallForecast(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                    showImageView();
                                }
                            });
                        } else {
                            mErrorMessage = "Error Occured! Please try again!";
                            alertUser();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "IO Exception caught:", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON Exception caught:", e);
                    }
                }
            });
        } else {
            mErrorMessage = "Internet is not available!";
            alertUser();
        }
    }

    private void showProgressBar() {
        mRefreshProgressBar.setVisibility(View.VISIBLE);
        mRefreshImageView.setVisibility(View.INVISIBLE);
    }

    private void showImageView() {
        mRefreshProgressBar.setVisibility(View.INVISIBLE);
        mRefreshImageView.setVisibility(View.VISIBLE);
    }

    private void updateDisplay() {
        Current current = mForecast.getCurrentWeather();
        mMainTextView.setText(current.getTemperature() + "");
        mTimeTextView.setText("Now " + current.getFormattedTime() +", it is");
        mHumidityDataTextView.setText(current.getHumidity() + "");
        mPrecipitationDataTextView.setText(current.getPrecipProbability() + "%");
        mSummary.setText(current.getSummary());
        mLocationTextView.setText(current.getTimeZone());
        Drawable myIcon = ContextCompat.getDrawable(getApplicationContext(),
                            current.getIconID());
        mIconImageView.setImageDrawable(myIcon);

    }

    private Forecast getOverallForecast(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();
        forecast.setCurrentWeather(getCurrentWeather(jsonData));
        forecast.setDailyWeather(getDailyWeather(jsonData));
        forecast.setHourlyWeather(getHourlyWeather(jsonData));
        return forecast;
    }

    private Hour[] getHourlyWeather(String jsonData) throws JSONException {
        JSONObject wholeObject = new JSONObject(jsonData);
        String timezone = wholeObject.getString("timezone");
        JSONObject hourly = wholeObject.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        int lenHourly = data.length();
        Hour[] hours = new Hour[lenHourly];

        for(int i=0;i<lenHourly;i++) {
            JSONObject temp = data.getJSONObject(i);
            Hour tempHour = new Hour();
            tempHour.setIcon(temp.getString("icon"));
            tempHour.setSummary(temp.getString("summary"));
            tempHour.setTemperature(temp.getDouble("temperature"));
            tempHour.setTime(temp.getLong("time"));
            tempHour.setTimeZone(timezone);
            hours[i] = tempHour;
        }
        return hours;
    }


    private Day[] getDailyWeather(String jsonData) throws JSONException {
        JSONObject wholeObject = new JSONObject(jsonData);
        String timezone = wholeObject.getString("timezone");
        JSONObject daily = wholeObject.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        int lenDaily = data.length();
        Day[] days = new Day[lenDaily];
        for(int i=0;i<lenDaily;i++) {
            JSONObject temp = data.getJSONObject(i);
            Day tempDay = new Day();
            tempDay.setIcon(temp.getString("icon"));
            tempDay.setSummary(temp.getString("summary"));
            tempDay.setMaxTemp(temp.getDouble("temperatureMax"));
            tempDay.setTime(temp.getLong("time"));
            tempDay.setTimeZone(timezone);
            days[i] = tempDay;
        }
        return days;
    }


    private Current getCurrentWeather(String jsonData) throws JSONException {
        JSONObject weatherObject = new JSONObject(jsonData);
        String timeZone = weatherObject.getString("timezone");
        JSONObject currently = weatherObject.getJSONObject("currently");
        String icon = currently.getString("icon");
        long time = currently.getLong("time");
        double temperature = currently.getDouble("temperature");
        double humidity = currently.getDouble("humidity");
        double precipProbability = currently.getDouble("precipProbability");
        String summary = currently.getString("summary");

        Current current = new Current();
        current.setIcon(icon);
        current.setTime(time);
        current.setTemperature(temperature);
        current.setHumidity(humidity);
        current.setPrecipProbability(precipProbability);
        current.setSummary(summary);
        current.setTimeZone(timeZone);

        Log.d(TAG, current.getFormattedTime());
//
//        Log.i(TAG, "TimeZone:" + timeZone);
//        Log.i(TAG, "icon:" + icon);
//        Log.i(TAG, "Time:" + time);
//        Log.i(TAG, "temperature:" + temperature + "");
//        Log.i(TAG, "humidity:" + humidity + "");
//        Log.i(TAG, "precipProbability:" + precipProbability + "");
//        Log.i(TAG, "summary:" + summary);

        return current;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager conManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUser() {
        Bundle bundle = new Bundle();
        bundle.putString("passed_msg", mErrorMessage);
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), "error dialog");
    }

}
