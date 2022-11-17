package com.api.teaeduc.utils;

import lombok.Getter;

import java.util.Map;

public class BusinessException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -73330199612679017L;

    @Getter
    private final String message;

    @Getter
    private final transient Map<String, Object> args;

    public BusinessException(String message) {
        this.message = message;
        this.args = null;
    }

    public BusinessException(String message, Map<String, Object> args) {
        this.message = message;
        this.args = args;
    }
}