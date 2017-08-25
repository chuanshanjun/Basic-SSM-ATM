package com.dayuanit.atm.test;

import com.dayuanit.atm.Exception.ATMException;
import com.dayuanit.atm.controller.CardController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created by YOUNG on 2017/8/24.
 */
public class LogTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        Logger log = LoggerFactory.getLogger(CardController.class);
        log.debug("wo shi debug");
        log.info("wo shi info");
        log.warn("wo shi warn");
        log.error("wo shi error");

        try {
            throw new ATMException("开户失败");
        } catch (Exception e) {
            log.error("ATM系统异常{}, {}, {}", e.getMessage(), "sx", 1111, e);
        }

        String msg = "oooxxx";
        //字符串转字符数组
        char[] s = msg.toCharArray();

        //字符数组转字符串
        String chars = String.valueOf(s);

        //字符串转字节数组
        byte[] b =msg.getBytes("UTF-8");
        System.out.println("b[]=" + b);

        //字节数组转字符串
        String msg2 = String.valueOf(b);

        System.out.println("msg2=" + msg2);
    }
}
