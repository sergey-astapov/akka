package com.reactiveapps.reactiveweb.commands;

import com.reactiveapps.reactiveweb.facts.ContinuousFact;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

public class GetContinuousFacts {
    public final String uid;

    public GetContinuousFacts(String uid) {
        this.uid = uid;
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("uid", uid)
                .toString();
    }

    public static class Result {
        public enum Status {SUCCESS, NOT_FOUND, ERROR}

        public final Status status;
        public final List<ContinuousFact> facts;

        public Result(Status status, List<ContinuousFact> facts) {
            this.status = status;
            this.facts = facts;
        }

        public static Result notFound() {
            return new Result(Status.NOT_FOUND, Collections.emptyList());
        }

        public static Result error() {
            return new Result(Status.ERROR, Collections.emptyList());
        }
    }
}
