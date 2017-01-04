package com.example.abmuthu.stormy.model;

import com.example.abmuthu.stormy.R;

/**
 * Created by abmuthu on 1/3/17.
 */

public class Forecast {
    private Current mCurrentWeather;
    private Hour[] mHourlyWeather;
    private Day[] mDailyWeather;

    public Current getCurrentWeather() {
        return mCurrentWeather;
    }

    public void setCurrentWeather(Current currentWeather) {
        mCurrentWeather = currentWeather;
    }

    public Hour[] getHourlyWeather() {
        return mHourlyWeather;
    }

    public void setHourlyWeather(Hour[] hourlyWeather) {
        mHourlyWeather = hourlyWeather;
    }

    public Day[] getDailyWeather() {
        return mDailyWeather;
    }

    public void setDailyWeather(Day[] dailyWeather) {
        mDailyWeather = dailyWeather;
    }
    
    public static int getIconID(String icon) {
        int iconID = -1;
        switch (icon) {
            case "clear-day":
                iconID = R.drawable.clear_day;
                break;
            case "clear-night":
                iconID = R.drawable.clear_night;
                break;
            case "rain":
                iconID = R.drawable.rain;
                break;
            case "snow":
                iconID = R.drawable.snow;
                break;
            case "sleet":
                iconID = R.drawable.sleet;
                break;
            case "wind":
                iconID = R.drawable.wind;
                break;
            case "fog":
                iconID = R.drawable.fog;
                break;
            case "cloudy":
                iconID = R.drawable.cloudy;
                break;
            case "partly-cloudy-day":
                iconID = R.drawable.partly_cloudy;
                break;
            default:
                iconID = R.drawable.clear_day;
        }

        return iconID;
    }
}
