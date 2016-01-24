package com.broduck.domain;

import java.util.Date;

/**
 * Created by broduck on 16. 1. 23.
 */
public class AttachVO {
    String fullName;
    Integer bno;
    Date regDate;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getBno() {
        return bno;
    }

    public void setBno(Integer bno) {
        this.bno = bno;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "AttachVO{" +
                "fullName='" + fullName + '\'' +
                ", bno=" + bno +
                ", regDate=" + regDate +
                '}';
    }
}
