package com.example.terrordetector;

import java.util.ArrayList;

public class ResultPerUserID {
    private String userID;
    private ArrayList <Result> resultArrayList;

    public ResultPerUserID(String alertID) {
        this.userID = alertID;
        this.resultArrayList = new ArrayList<Result>();
    }

    public String getUserID() {
        return userID;
    }
    public  ArrayList<Result> addResultToArray(String alertID,String website, String location, String timestamp, String feedback, String text, String publisher, String keywords){
       resultArrayList.add( new Result(alertID,website,location,timestamp,feedback,text,publisher,keywords));
    return  resultArrayList;
    }
    public  ArrayList<Result> addToArray(ArrayList<Result> arrayListR){
        resultArrayList= arrayListR;
        return  resultArrayList;
    }

    public ResultPerUserID setResultArrayList(ArrayList<Result> resultArrayList) {
        this.resultArrayList = resultArrayList;
        return this;
    }
}
