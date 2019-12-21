package com.example.sistemarehabilitacion.BaseDatos.Modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {//CLASE ESTANDAR CON CAMPOS QUE RESPONDE LA API

    @SerializedName("status")
    @Expose
    private  String status;

    @SerializedName("code")
    @Expose
    private  int code;

    @SerializedName("message")
    @Expose
    private  String message;

    public Request(String status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public Request() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
