package com.aizz.mindmingle.domain.vo;

import java.io.Serializable;

/**
 * 统计数据响应VO
 */
public class StatsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    public Integer totalTools;
    public Integer totalUsers;
    public Integer totalSubmissions;
    public Integer todaySubmissions;

    public StatsVO() {
    }

    public Integer getTotalTools() {
        return totalTools;
    }

    public void setTotalTools(Integer totalTools) {
        this.totalTools = totalTools;
    }

    public Integer getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Integer totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Integer getTotalSubmissions() {
        return totalSubmissions;
    }

    public void setTotalSubmissions(Integer totalSubmissions) {
        this.totalSubmissions = totalSubmissions;
    }

    public Integer getTodaySubmissions() {
        return todaySubmissions;
    }

    public void setTodaySubmissions(Integer todaySubmissions) {
        this.todaySubmissions = todaySubmissions;
    }

    @Override
    public String toString() {
        return "StatsVO{" +
                "totalTools=" + totalTools +
                ", totalUsers=" + totalUsers +
                ", totalSubmissions=" + totalSubmissions +
                ", todaySubmissions=" + todaySubmissions +
                '}';
    }
}
