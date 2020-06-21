package org.project.poolreservation;


public class customer {
    private String time;
    private String sex;
    private int reserves;
    public String customerId;
    public int sectionId;
    public String token;

    public customer(String token,String time, String sex, int reserves,String customerId,int sectionId){
        this.token=token;
        this.sectionId=sectionId;
        this.customerId=customerId;
        this.reserves=reserves;
        this.time=time;
        this.sex=sex;

    }

    public void setReserves(int reserves) {
        this.reserves = reserves;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getReserves() {
        return reserves;
    }

    public String getSex() {
        return sex;
    }
//change1
    public String getTime() {
        return time;
    }


    @Override
    public String toString() {
        return time;
    }
}
