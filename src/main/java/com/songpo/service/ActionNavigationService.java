package com.songpo.service;

import com.songpo.mapper.SlActionNavigationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionNavigationService {

    @Autowired
    private SlActionNavigationMapper slActionNavigationMapper;
}
