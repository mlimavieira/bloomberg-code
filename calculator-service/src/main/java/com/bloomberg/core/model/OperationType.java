package com.bloomberg.core.model;

public enum OperationType {

    ADD,SUB, DIV, MULT, UNKNOWN;

    public static OperationType getByName(String name) {
        for (OperationType type : values()) {
            if(type.name().equalsIgnoreCase(name)){
                return type;
            }
        }

        return UNKNOWN;
    }
}
