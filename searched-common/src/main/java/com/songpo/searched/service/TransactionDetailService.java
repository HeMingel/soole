package com.songpo.searched.service;

import com.songpo.searched.entity.SlTransactionDetail;
import com.songpo.searched.mapper.SlTransactionDetailMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 刘松坡
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TransactionDetailService extends BaseService<SlTransactionDetail, String> {

    public TransactionDetailService(SlTransactionDetailMapper mapper) {
        super(mapper);
    }
}
