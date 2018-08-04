package com.songpo.searched.mapper;

import com.songpo.searched.entity.SlSlbTransaction;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CmSlbTransactionMapper {

    /**
     *  查询网页注册的用户搜了贝
     */
    List<SlSlbTransaction> selectSlb();

}
