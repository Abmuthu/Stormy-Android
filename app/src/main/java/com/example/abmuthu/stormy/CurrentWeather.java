package com.example.abmuthu.stormy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by abmuthu on 12/29/16.
 */

public class CurrentWeather {

    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipProbability;
    private String mSummary;
    private String mTimeZone;


    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getIcon() {
        return mIcon;
    }

    public int getIconID() {
//        clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
        int iconID = -1;
        switch (mIcon) {
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

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTime() {
        return mTime;
    }

    public String getFormattedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime() * 1000);
        String timeString =  formatter.format(dateTime);
        return timeString;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getTemperature() {
        return (int) Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public int getPrecipProbability() {
        double precipPercentage = mPrecipProbability * 100;
        return (int)Math.round(precipPercentage);
    }

    public void setPrecipProbability(double precipProbability) {
        mPrecipProbability = precipProbability;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }



}
