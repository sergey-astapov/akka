package com.passgen.core.model;

import org.apache.commons.lang.builder.EqualsBuilder;

import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

public class GeneratePassword {
    public final int length;

    public GeneratePassword(int length) {
        this.length = length;
    }

    public String toString() {
        return reflectionToString(this);
    }

    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    public static GeneratePassword withLength(int length) {
        return new GeneratePassword(length);
    }
}
