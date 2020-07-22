package com.example.candidatetracker.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Duration {

    private Integer days;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "en_IN", timezone = "Asia/Kolkata")
    private Date start;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "en_IN", timezone = "Asia/Kolkata")
    private Date end;

    public Duration() {
    }

    public Duration(Integer days, Date start, Date end) {
        this.days = days;
        this.start = start;
        this.end = end;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "Duration{" +
                "days=" + days +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
