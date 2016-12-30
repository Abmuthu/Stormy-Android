package com.example.abmuthu.stormy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private String mErrorMessage = null;
    private CurrentWeather mCurrentWeather = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "5df33850f2b800a4e706f7bfe967ae2b";
        double latitude = 37.8267;
        double longitude = -122.4233;
        String apiURL = "https://api.darksky.net/forecast/" + apiKey + "/" + latitude + "," + longitude;
        if(isNetworkAvailable()) {
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
                            mCurrentWeather = getCurrentWeather(jsonData);
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

    private CurrentWeather getCurrentWeather(String jsonData) throws JSONException {
        JSONObject weatherObject = new JSONObject(jsonData);
        String timeZone = weatherObject.getString("timezone");
        JSONObject currently = weatherObject.getJSONObject("currently");
        String icon = currently.getString("icon");
        long time = currently.getLong("time");
        double temperature = currently.getDouble("temperature");
        double humidity = currently.getDouble("humidity");
        double precipProbability = currently.getDouble("precipProbability");
        String summary = currently.getString("summary");

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setIcon(icon);
        currentWeather.setTime(time);
        currentWeather.setTemperature(temperature);
        currentWeather.setHumidity(humidity);
        currentWeather.setPrecipProbability(precipProbability);
        currentWeather.setSummary(summary);
        currentWeather.setTimeZone(timeZone);

        Log.d(TAG, currentWeather.getFormattedTime());
//
//        Log.i(TAG, "TimeZone:" + timeZone);
//        Log.i(TAG, "icon:" + icon);
//        Log.i(TAG, "Time:" + time);
//        Log.i(TAG, "temperature:" + temperature + "");
//        Log.i(TAG, "humidity:" + humidity + "");
//        Log.i(TAG, "precipProbability:" + precipProbability + "");
//        Log.i(TAG, "summary:" + summary);

        return currentWeather;
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
