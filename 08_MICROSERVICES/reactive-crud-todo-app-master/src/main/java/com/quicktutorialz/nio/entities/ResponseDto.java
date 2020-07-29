package com.quicktutorialz.nio.entities;

import java.util.Date;

/**
 * @author alessandroargentieri
 *
 * this pojo is used to wrap the HTTP generic response
 */
public class ResponseDto {
    private int status;
    private Object response;
    private Date datetime;

    public ResponseDto(){}

    public ResponseDto(int status, Object response) {
        this.status = status;
        this.response = response;
        this.datetime = new Date();
    }
}
