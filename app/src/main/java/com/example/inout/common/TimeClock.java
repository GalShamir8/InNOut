package com.example.inout.common;

import androidx.annotation.NonNull;

import java.util.Locale;

public class TimeClock implements Comparable{
    private int hour;
    private int minute;

    public TimeClock() { }

    public TimeClock(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public int compareTo(Object other) {
        if (!(other instanceof TimeClock)){
            throw new IllegalArgumentException();
        }
        int otherHour = ((TimeClock) other).getHour();
        int otherMinute = ((TimeClock) other).getMinute();
        if(hour == otherHour){
            return Integer.compare(minute, otherMinute);
        }
        return Integer.compare(hour , otherHour);
    }

    @NonNull
    @Override
    public String toString(){
        return String.format(Locale.US,"%d:%d", hour, minute);
    }
}
