package com.example.appbaothuc.alarmsetting;

public class YourRingTone {
    private String nameRingTone;
    private String uriRingTone;

    public YourRingTone(String nameRingTone, String uriRingTone) {
        this.nameRingTone = nameRingTone;
        this.uriRingTone = uriRingTone;
    }

    public String getNameRingTone() {
        return nameRingTone;
    }

    public void setNameRingTone(String nameRingTone) {
        this.nameRingTone = nameRingTone;
    }

    public String getUriRingTone() {
        return uriRingTone;
    }

    public void setUriRingTone(String uriRingTone) {
        this.uriRingTone = uriRingTone;
    }
}
