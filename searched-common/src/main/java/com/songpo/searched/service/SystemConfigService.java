package com.songpo.searched.service;

import com.songpo.searched.entity.SlSystemConfig;
import com.songpo.searched.mapper.SlSystemConfigMapper;
import org.springframework.stereotype.Service;

/**
 * @author 刘松坡
 */
@Service
public class SystemConfigService extends BaseService<SlSystemConfig, String> {

    public SystemConfigService(SlSystemConfigMapper mapper) {
        super(mapper);
    }
}
