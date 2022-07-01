package com.example.inout.models;

import com.example.inout.common.TimeClock;

public class DayData {
    private TimeClock start;
    private TimeClock end;

    public DayData() {
        start = new TimeClock();
        end = new TimeClock();
    }

    public TimeClock getStart() {
        return start;
    }

    public DayData setStart(TimeClock start) {
        this.start = start;
        return this;
    }

    public TimeClock getEnd() {
        return end;
    }

    public DayData setEnd(TimeClock end) {
        this.end = end;
        return this;
    }
}
