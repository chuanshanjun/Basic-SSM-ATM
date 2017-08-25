package com.dayuanit.atm.vo;

/**
 * Created by YOUNG on 2017/8/24.
 */
public class TransferVO {

    private int amount;

    private int cardId;

    private String inCardNum;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getInCardNum() {
        return inCardNum;
    }

    public void setInCardNum(String inCardNum) {
        this.inCardNum = inCardNum;
    }
}
