package com.dayuanit.atm.test;

import static org.junit.Assert.assertEquals;

import com.dayuanit.atm.domain.Detail;
import com.dayuanit.atm.mapper.DetailMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by YOUNG on 2017/8/23.
 */
@ContextConfiguration("/spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DetailMapperTest {

    private Detail detail;

    @Autowired
    private DetailMapper detailMapper;

    @Before
    public void init() {
        detail = new Detail();
        detail.setUserId(4);
        detail.setCardId(9);
        detail.setAmount(100);
        detail.setOptType(1);
        detail.setFlowDesc("存款");
    }

    @Test
    @Rollback
    public void testAddDetail() {
        int rows = detailMapper.addDetail(detail);
        assertEquals(1, rows);
    }

    @Test
    @Rollback
    public void testCountDetail() {
        detailMapper.addDetail(detail);
        int rows = detailMapper.countDetail(detail.getCardId());
        assertEquals(2, rows);
    }

    @Test
    @Rollback
    public void testListDetail() {
        List list = detailMapper.listDetail(9,0,2);
        assertEquals(2, list.size());
    }
}
