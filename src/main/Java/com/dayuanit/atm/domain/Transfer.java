package com.dayuanit.atm.domain;

import java.util.Date;

/**
 * Created by YOUNG on 2017/8/24.
 */
public class Transfer {

    private Integer id;

    private String inCardNum;

    private String outCardNum;

    private Integer inCardId;

    private Integer outCardId;

    private Integer amount;

    private Integer status;

    private Date createTime;

    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInCardNum() {
        return inCardNum;
    }

    public void setInCardNum(String inCardNum) {
        this.inCardNum = inCardNum;
    }

    public String getOutCardNum() {
        return outCardNum;
    }

    public void setOutCardNum(String outCardNum) {
        this.outCardNum = outCardNum;
    }

    public Integer getInCardId() {
        return inCardId;
    }

    public void setInCardId(Integer inCardId) {
        this.inCardId = inCardId;
    }

    public Integer getOutCardId() {
        return outCardId;
    }

    public void setOutCardId(Integer outCardId) {
        this.outCardId = outCardId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
