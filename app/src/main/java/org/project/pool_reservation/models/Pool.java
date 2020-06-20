package org.project.pool_reservation.models;

public class Pool {
    private String poolName;

    public Pool(String poolName) {
        this.poolName = poolName;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }
}
