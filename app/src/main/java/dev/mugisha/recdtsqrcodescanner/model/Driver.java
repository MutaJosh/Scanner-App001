package dev.mugisha.recdtsqrcodescanner.model;

public class Driver {
private String NID,code;

    public Driver(String NID, String code) {
        this.NID = NID;
        this.code = code;
    }

    public Driver() {
    }

    public String getNID() {
        return NID;
    }

    public void setNID(String NID) {
        this.NID = NID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
