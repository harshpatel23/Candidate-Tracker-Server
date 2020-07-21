package com.example.candidatetracker.demo.entity;

public class Stats {

    private long ready;

    private long hold;

    private long hired;

    private long rejected;

    private long total;

    public Stats(long ready, long hold, long hired, long rejected, long total) {
        this.ready = ready;
        this.hold = hold;
        this.hired = hired;
        this.rejected = rejected;
        this.total = total;
    }

    public Stats() {
    }

    public long getReady() {
        return ready;
    }

    public void setReady(long ready) {
        this.ready = ready;
    }

    public long getHold() {
        return hold;
    }

    public void setHold(long hold) {
        this.hold = hold;
    }

    public long getHired() {
        return hired;
    }

    public void setHired(long hired) {
        this.hired = hired;
    }

    public long getRejected() {
        return rejected;
    }

    public void setRejected(long rejected) {
        this.rejected = rejected;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "ready=" + ready +
                ", hold=" + hold +
                ", hired=" + hired +
                ", rejected=" + rejected +
                ", total=" + total +
                '}';
    }
}
