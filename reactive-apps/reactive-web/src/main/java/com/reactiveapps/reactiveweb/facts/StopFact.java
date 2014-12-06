package com.reactiveapps.reactiveweb.facts;

public class StopFact implements ContinuousFact {
    public final String uid;
    public final String data;
    public final Long total;

    public StopFact(String uid, String data, Long total) {
        this.uid = uid;
        this.data = data;
        this.total = total;
    }

    public String toString() {
        return "uid: " + uid +
                ", total: " + total +
                ", data: " + data;
    }
}
