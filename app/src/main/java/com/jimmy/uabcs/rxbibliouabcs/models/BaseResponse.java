package com.jimmy.uabcs.rxbibliouabcs.models;

import com.google.gson.annotations.Expose;

public class BaseResponse {
    //region Fields
    @Expose
    private boolean Success;
    @Expose
    private String Message;
    //endregion

    //region Getters & Setters

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    //endregion
}