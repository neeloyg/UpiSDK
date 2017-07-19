package com.upi.sdk.domain;

import com.rssoftware.upiint.schema.ScheduleType;

/**
 * Created by Neeloy on 03-01-2017.
 */
public class Schedule {

//            "startDate": "2018-02-07",


    private ScheduleType schdType;
    private String schdName;
    private String ownerId;
    private String ownVpa;
    private String otherVpa;
    private double amount;
    private String frequncy;
    private String recurDate;
    private int recurFreq;
    private String startDate;
    private String status;
    private int schdId;

    public int getRecurCount() {
        return recurCount;
    }

    public void setRecurCount(int recurCount) {
        this.recurCount = recurCount;
    }

    public int getAlertBefore() {
        return alertBefore;
    }

    public void setAlertBefore(int alertBefore) {
        this.alertBefore = alertBefore;
    }

    private int recurCount;
    private int alertBefore;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSchdId() {
        return schdId;
    }

    public void setSchdId(int schdId) {
        this.schdId = schdId;
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

    public String getFrequncy() {
        return frequncy;
    }

    public void setFrequncy(String frequncy) {
        this.frequncy = frequncy;
    }

    public String getRecurDate() {
        return recurDate;
    }

    public void setRecurDate(String recurDate) {
        this.recurDate = recurDate;
    }

    public int getRecurFreq() {
        return recurFreq;
    }

    public void setRecurFreq(int recurFreq) {
        this.recurFreq = recurFreq;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


}
