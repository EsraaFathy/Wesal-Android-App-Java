package com.example.wesal;

public class MapModel {
    private String charityName;
    private String latitude;
    private String longitude;

    public MapModel(String charityName, String latitude, String longitude) {
        this.charityName = charityName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCharityName() {
        return charityName;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
