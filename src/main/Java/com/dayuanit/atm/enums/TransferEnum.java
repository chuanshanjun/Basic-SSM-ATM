package com.dayuanit.atm.enums;

import com.dayuanit.atm.Exception.ATMException;

/**
 * Created by YOUNG on 2017/8/24.
 */
public enum TransferEnum {

    un_transfer(1, "待转账"), already_transfer(2, "已转账"), cancle(3, "取消");

    private int k;

    private String v;

    private TransferEnum(int k, String v) {
        this.k = k;
        this.v = v;
    }

    public static TransferEnum getEnum(int status) {
        for (TransferEnum te : TransferEnum.values()) {
            if (te.getK() == status) {
                return te;
            }
        }

        throw new ATMException("转账异常");
    }

    public int getK() {
        return k;
    }

    public String getV() {
        return v;
    }
}
