package com.reactiveapps.reactiveweb.facts;

public class InnerFact implements ContinuousFact {
    public final String uid;
    public final String data;

    public InnerFact(String uid, String data) {
        this.uid = uid;
        this.data = data;
    }

    public String toString() {
        return "uid: " + uid +
                ", data: " + data;
    }
}
