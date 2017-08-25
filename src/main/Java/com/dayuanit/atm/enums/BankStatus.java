package com.dayuanit.atm.enums;

import com.dayuanit.atm.Exception.ATMException;

/**
 * Created by YOUNG on 2017/8/22.
 */
public enum BankStatus {

    ENABLE(0, "可用"), DISABLE(1, "冻结"), DELETE(2, "已删除");

    private int code;
    private String v;

    private BankStatus(int code, String v) {
        this.code = code;
        this.v = v;
    }

    public static BankStatus getEnum(int code) {
        for (BankStatus bs : BankStatus.values()) {
            if (code == bs.getCode()) {
                return bs;
            }
        }

        throw new ATMException("没有适合的银行卡状态");
    }

    public int getCode() {
        return code;
    }

    public String getV() {
        return v;
    }

}
