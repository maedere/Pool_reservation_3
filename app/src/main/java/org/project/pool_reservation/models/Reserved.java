package org.project.poolreservation.models;

public class Reserved {
    String time;
    String day;
    String date;
    String gender;
    String poolName;
    String code;

    public Reserved(String time,String day, String date, String gender,String poolName,String code){
        this.time=time;
        this.day=day;
        this.date=date;
        this.gender=gender;
        this.poolName=poolName;
        this.code=code;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPoolName() {
        return poolName;
    }
    public void setPoolName(String poolName) {
        this.poolName= poolName;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

}
