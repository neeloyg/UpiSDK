package com.rssoftware.upiint.schema;

/**
 * Created by NeeloyG on 13-06-2017.
 */

public class AddUpdateUserLimit {


    protected String userId;
    protected String handler;
    protected String monthlyLimit;
    protected String monthlyVol;
    protected String weeklyLimit;
    protected String weeklyVol;
    protected String dailyLimit;
    protected String dailyVol;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


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
