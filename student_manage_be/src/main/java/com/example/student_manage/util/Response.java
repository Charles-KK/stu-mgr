package com.example.student_manage.util;

import lombok.Data;

@Data
public class Response {

    public static final String OK = "200";
    public static final String FAILED = "210";
    public static final String NOT_FOUND = "404";
    public static final String ERROR = "500";
    public static final String BAD_REQUEST = "400";
    public static final String PERMISSION_DENY = "301";

    private String code;

    private String message;

    private Object data;

    public Response() {
        this.code = OK;
    }


    public Response data(Object d) {
        this.data = d;
        return this;
    }

    public Response message(String m) {
        this.message = m;
        return this;
    }

    public Response code(String c) {
        this.code = c;
        return this;
    }

}
