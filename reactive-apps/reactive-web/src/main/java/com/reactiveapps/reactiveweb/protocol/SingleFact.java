package com.reactiveapps.reactiveweb.protocol;

public class SingleFact implements Fact {
    public final String uid;
    public final String data;

    public SingleFact(String uid, String data) {
        this.uid = uid;
        this.data = data;
    }

    public String toString() {
        return "uid: " + uid +
               ", data: " + data;
    }
}
