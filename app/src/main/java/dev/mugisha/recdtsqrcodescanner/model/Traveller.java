package dev.mugisha.recdtsqrcodescanner.model;

public class Traveller {

    private String code;
    private String NID;

    public Traveller() {
    }

    public Traveller(String code, String NID) {
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
