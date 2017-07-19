package com.upi.sdk.domain;

/**
 * Created by NeeloyG on 13-06-2017.
 */

public class InputAddUpdateUserLimit {

    private String handler;
    private String monthlyLimit;
    private String monthlyVol;
    private String weeklyLimit;
    private String weeklyVol;
    private String dailyLimit;
    private String dailyVol;




    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(String monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public String getMonthlyVol() {
        return monthlyVol;
    }

    public void setMonthlyVol(String monthlyVol) {
        this.monthlyVol = monthlyVol;
    }

    public String getWeeklyLimit() {
        return weeklyLimit;
    }

    public void setWeeklyLimit(String weeklyLimit) {
        this.weeklyLimit = weeklyLimit;
    }

    public String getWeeklyVol() {
        return weeklyVol;
    }

    public void setWeeklyVol(String weeklyVol) {
        this.weeklyVol = weeklyVol;
    }

    public String getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(String dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public String getDailyVol() {
        return dailyVol;
    }

    public void setDailyVol(String dailyVol) {
        this.dailyVol = dailyVol;
    }



}
