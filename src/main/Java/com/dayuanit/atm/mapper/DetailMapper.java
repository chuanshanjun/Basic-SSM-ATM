package com.dayuanit.atm.mapper;

import com.dayuanit.atm.domain.Detail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by YOUNG on 2017/8/23.
 */
public interface DetailMapper {

    int addDetail(Detail detail);

    int countDetail(@Param("cardId") Integer cardId);

    List listDetail(@Param("cardId") Integer cardId,
                    @Param("offSet") Integer offSet, @Param("prePageNum")Integer prePageNum);

}
