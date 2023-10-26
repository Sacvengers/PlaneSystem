package com.scavengers.plane.system.entity;

/**
 * @Project: ticketSystem
 * @Date: 2023/10/20 18:49
 * @author: Scavengers
 * @Description: 飞机实体类
 */
public class Plane {
    private String flightID;
    private String startPlace;
    private String endPlace;
    private String stratDate;
    private String endTime = "1";
    private String startTime;
    private String model;  //机型
    private int mile; //里程
    private String airCompany;
    private String high;
    private String middle;
    private String low;

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartplace(String startplace) {
        this.startPlace = startplace;
    }

    public String getEndplace() {
        return endPlace;
    }

    public void setEndplace(String endplace) {
        this.endPlace = endplace;
    }

    public String getStratDate() {
        return stratDate;
    }

    public void setStratDate(String stratDate) {
        this.stratDate = stratDate;
    }

    public void setStarttime(String starttime) {
        this.startTime = starttime;
    }

    public String getStarttime() {
        return startTime;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMile() {
        return mile;
    }

    public void setMile(int mile) {
        this.mile = mile;
    }

    public String getAirCompany() {
        return airCompany;
    }

    public void setAirCompany(String airCompany) {
        this.airCompany = airCompany;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getHigh() {
        return high;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getMiddle() {
        return middle;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getLow() {
        return low;
    }
}
