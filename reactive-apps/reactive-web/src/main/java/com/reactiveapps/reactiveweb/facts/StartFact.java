package com.reactiveapps.reactiveweb.facts;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class StartFact implements ContinuousFact {
    public final String uid;
    public final String data;

    public StartFact(String uid, String data) {
        this.uid = uid;
        this.data = data;
    }

    @Override
    public String getUid() {
        return uid;
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(uid)
                .append(data)
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        StartFact rhs = (StartFact) obj;
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
