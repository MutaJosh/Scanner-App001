package dev.mugisha.recdtsqrcodescanner.model;

import com.google.gson.annotations.SerializedName;

public class Driver {

    private String code;
private String NID;

    public Driver() {
    }

    public Driver(String code, String NID) {
        this.code = code;
        this.NID = NID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNID() {
        return NID;
    }

    public void setNID(String NID) {
        this.NID = NID;
    }
}
