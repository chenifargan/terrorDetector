package com.example.terrordetector;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("alertId")
    private String alertid;
    @SerializedName("website")
    private String website;
    @SerializedName("location")
    private String location;
    @SerializedName("timestamp")
    private  String timestamp;
    @SerializedName("feedback")
    private String feedback;
    @SerializedName("text")
    private String text;
    @SerializedName("publisher")
    private String publisher;
    @SerializedName("keywords")
    private String keywords;

    public Result() {
    }

    public Result(String alertId,String website, String location, String timestamp, String feedback, String text, String publisher, String keywords) {
        this.alertid = alertId;
        this.website = website;
        this.location = location;
        this.timestamp = timestamp;
        this.feedback = feedback;
        this.text = text;
        this.publisher = publisher;
        this.keywords = keywords;
    }


    public String getWebsite() {
        return website;
    }

    public String getLocation() {
        return location;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getText() {
        return text;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getAlertid() {
        return alertid;
    }

    public Result setFeedback(String feedback) {
        this.feedback = feedback;
        return this;
    }
}
