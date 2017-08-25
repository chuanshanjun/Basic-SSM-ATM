package com.dayuanit.atm.enums;

import com.dayuanit.atm.Exception.ATMException;

/**
 * Created by YOUNG on 2017/8/23.
 */
public enum optType {

    DEPOSIT(1, "存款"), DRAW(2, "取款"), TRANSFEROUT(3, "转账支出"), TRANSFERIN(4, "转账收入");

    private int code;
    private String v;

    private optType(int code, String v) {
        this.code = code;
        this.v = v;
    }

    public static optType getEnum(int code) {
        for (optType ot : optType.values()) {
            if (code == ot.getCode()) {
                return ot;
            }
        }

        throw new ATMException("没有找到匹配的信息");
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
