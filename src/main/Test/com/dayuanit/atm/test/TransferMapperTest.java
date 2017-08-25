package com.dayuanit.atm.test;

import static org.junit.Assert.assertEquals;

import com.dayuanit.atm.domain.Transfer;
import com.dayuanit.atm.mapper.TransferMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Target;

/**
 * Created by YOUNG on 2017/8/24.
 */
@ContextConfiguration("/spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TransferMapperTest {

    private Transfer transfer;

    @Autowired
    private TransferMapper transferMapper;

    @Before
    public void init() {
        transfer = new Transfer();
        transfer.setInCardId(9);
        transfer.setOutCardId(10);
        transfer.setInCardNum("5555");
        transfer.setOutCardNum("6666");
        transfer.setAmount(1000);
        transfer.setStatus(1);
    }

    @Test
    @Rollback
    public void testAdd() {
        int rows = transferMapper.add(transfer);
        assertEquals(1, rows);
    }
}
