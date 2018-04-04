package com.songpo.searched.service;

import com.songpo.searched.entity.SlMessage;
import com.songpo.searched.mapper.SlMessageMapper;
import org.springframework.stereotype.Service;

/**
 * @author 刘松坡
 */
@Service
public class MessageService extends BaseService<SlMessage, String> {

    public MessageService(SlMessageMapper mapper) {
        super(mapper);
    }
}
