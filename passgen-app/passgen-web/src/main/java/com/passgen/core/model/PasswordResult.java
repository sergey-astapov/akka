package com.passgen.core.model;

import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

public class PasswordResult {
    public enum Status {SUCCESS, ERROR}

    public final Status status;
    public final String password;
    public final String error;

    public PasswordResult(Status status, String password, String error) {
        this.status = status;
        this.password = password;
        this.error = error;
    }

    public String toString() {
        return reflectionToString(this);
    }

    public static PasswordResult withPassword(String password) {
        return new PasswordResult(Status.SUCCESS, password, "");
    }

    public static PasswordResult withError(String error) {
        return new PasswordResult(Status.ERROR, "", error);
    }
}
