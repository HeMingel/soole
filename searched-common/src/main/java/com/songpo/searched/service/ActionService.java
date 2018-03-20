package com.songpo.searched.service;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlAction;
import com.songpo.searched.entity.SlActionNavigation;
import com.songpo.searched.mapper.SlActionMapper;
import com.songpo.searched.mapper.SlActionNavigationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Slf4j
@Service
public class ActionService extends BaseService<SlAction, String> {

    public ActionService(SlActionMapper mapper) {
        super(mapper);
    }


}
