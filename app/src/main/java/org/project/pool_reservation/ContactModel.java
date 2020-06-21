package org.project.pool_reservation6;
public class ContactModel {
    private String time;
    private String sex;
    private int reserves;

 /*   public ContactModel(String time,String sex,int reserves){
        this.reserves=reserves;
        this.time=time;
        this.sex=sex;

    }*/

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

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return time;
    }
}
