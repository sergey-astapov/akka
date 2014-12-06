package com.reactiveapps.reactiveweb.facts;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class StopFact implements ContinuousFact {
    public final String uid;
    public final String data;
    public final Long total;

    public StopFact(String uid, String data, Long total) {
        this.uid = uid;
        this.data = data;
        this.total = total;
    }

    @Override
    public String getUid() {
        return uid;
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(uid)
                .append(total)
                .append(data)
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        StopFact rhs = (StopFact) obj;
        return new EqualsBuilder()
                .append(uid, rhs.uid)
                .append(total, rhs.total)
                .append(data, rhs.data)
                .isEquals();
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("uid", uid)
                .append("total", total)
                .append("data", data)
                .toString();
    }
}
