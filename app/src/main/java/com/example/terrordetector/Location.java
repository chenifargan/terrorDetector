package com.example.terrordetector;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
    private double  latitude;
    private double longitude;
    private String text ;


    public Location(double latitude, double longitude, String text) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.text = text;
    }

    protected Location(Parcel in) {
        latitude = Double.parseDouble(in.readString());
        longitude = Double.parseDouble(in.readString());
        text = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public Location setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Location setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(String.valueOf(latitude));
        dest.writeString(String.valueOf(longitude));
        dest.writeString(text);
    }

    public String getText() {
        return text;
    }

    public Location setText(String text) {
        this.text = text;
        return this;
    }
}
