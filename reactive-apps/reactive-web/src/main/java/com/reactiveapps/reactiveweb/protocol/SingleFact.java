package com.reactiveapps.reactiveweb.protocol;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SingleFact implements Fact {
    public final String uid;
    public final String data;

    public SingleFact(String uid, String data) {
        this.uid = uid;
        this.data = data;
    }

    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        SingleFact rhs = (SingleFact) obj;
        return new EqualsBuilder()
                .append(uid, rhs.uid)
                .append(data, rhs.data)
                .isEquals();
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("uid", uid)
                .append("data", data)
                .toString();
    }
}
