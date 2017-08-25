package com.dayuanit.atm.mapper;

import com.dayuanit.atm.domain.Transfer;

import java.util.List;

/**
 * Created by YOUNG on 2017/8/24.
 */
public interface TransferMapper {

    int add(Transfer transfer);

    int updateStatus(Transfer transfer);

    List<Transfer> listTransfer4In();
}
