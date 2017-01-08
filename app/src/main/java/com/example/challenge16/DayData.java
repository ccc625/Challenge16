package com.example.challenge16;

/**
 * Created by 지민 on 2016-12-04.
 */

public class DayData
{
    private String day;
    private String hour;
    private String min;
    private String message;
    private String weather;

    public DayData()
    {
        day = "";
        hour = "";
        min = "";
        message = "";
        weather = "";
    }

    public void setDay(String value)
    {
        this.day = value;
    }

    public String getDay()
    {
        return this.day;
    }

    public void setHour(String value)
    {
        this.hour = value;
    }

    public String getHour()
    {
        return this.hour;
    }

    public void setMin(String value)
    {
        this.min = value;
    }

    public String getMin()
    {
        return this.min;
    }

    public void setMessage(String value)
    {
        this.message = value;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setWeather(String value)
    {
        this.weather = value;
    }

    public String getWeather()
    {
        return this.weather;
    }
}
