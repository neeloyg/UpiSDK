package com.upi.sdk.domain;

import com.rssoftware.upiint.schema.ScheduleFrequency;
import com.rssoftware.upiint.schema.ScheduleType;

/**
 * Created by Neeloy on 03-01-2017.
 */
public class InputSchedule {

//            "startDate": "2018-02-07",
//            "startTime": "13:32:34",

    private ScheduleType schdType;
    private String schdName;
    private String ownerId;
    private String ownVpa;
    private String otherVpa;
    private double amount;
    private ScheduleFrequency frequncy;
    private String recurDate;
    private String startDate;
    private String alertBefore;
    private int recurCount;


    public ScheduleFrequency getFrequncy() {
        return frequncy;
    }

    public void setFrequncy(ScheduleFrequency frequncy) {
        this.frequncy = frequncy;
    }


    public String getAlertBefore() {
        return alertBefore;
    }

    public void setAlertBefore(String alertBefore) {
        this.alertBefore = alertBefore;
    }

    public int getRecurCount() {
        return recurCount;
    }

    public void setRecurCount(int recurCount) {
        this.recurCount = recurCount;
    }



    public ScheduleType getSchdType() {
        return schdType;
    }

    public void setSchdType(ScheduleType schdType) {
        this.schdType = schdType;
    }


    public String getSchdName() {
        return schdName;
    }

    public void setSchdName(String schdName) {
        this.schdName = schdName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnVpa() {
        return ownVpa;
    }

    public void setOwnVpa(String ownVpa) {
        this.ownVpa = ownVpa;
    }

    public String getOtherVpa() {
        return otherVpa;
    }

    public void setOtherVpa(String otherVpa) {
        this.otherVpa = otherVpa;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public String getRecurDate() {
        return recurDate;
    }

    public void setRecurDate(String recurDate) {
        this.recurDate = recurDate;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


}
