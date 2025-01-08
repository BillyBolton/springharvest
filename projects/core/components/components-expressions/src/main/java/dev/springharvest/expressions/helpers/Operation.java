package dev.springharvest.expressions.helpers;

import lombok.Getter;

public enum Operation {
    SEARCH("search"),
    COUNT("count");

    @Getter
    private String name;

    Operation(String name) {
        this.name = name;
    }
}
