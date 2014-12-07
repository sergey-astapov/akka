package com.reactiveapps.reactiveweb.protocol;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

public class GetContinuousState {
    public final String uid;

    public GetContinuousState(String uid) {
        this.uid = uid;
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("uid", uid)
                .toString();
    }

    public static GetContinuousState withUid(String uid) {
        return new GetContinuousState(uid);
    }

    public static class Result {
        public enum Status {SUCCESS, NOT_FOUND, ERROR}

        public final Status status;
        public final List<ContinuousFact> facts;

        public Result(Status status, List<ContinuousFact> facts) {
            this.status = status;
            this.facts = facts;
        }

        public boolean equals(Object obj) {
            if (obj == null) { return false; }
            if (obj == this) { return true; }
            if (obj.getClass() != getClass()) {
                return false;
            }
            Result rhs = (Result) obj;
            return new EqualsBuilder()
                    .append(status, rhs.status)
                    .append(facts, rhs.facts)
                    .isEquals();
        }

        public static Result success(List<ContinuousFact> facts) {
            return new Result(Status.SUCCESS, facts);
        }

        public static Result notFound() {
            return new Result(Status.NOT_FOUND, Collections.emptyList());
        }

        public static Result error() {
            return new Result(Status.ERROR, Collections.emptyList());
        }
    }
}
