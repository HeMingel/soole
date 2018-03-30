package com.songpo.searched.service;

import com.songpo.searched.entity.SlMessage;
import com.songpo.searched.mapper.SlMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 刘松坡
 */
@Service
public class NotificationService {

    @Autowired
    private SlMessageMapper messageMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 发送消息到指定单播队列
     *
     * @param channelId 消息频道
     * @param message   消息内容
     */
    public void sendToQueue(String channelId, SlMessage message) {
        this.messagingTemplate.convertAndSend("/queue/" + channelId, message);
        message.setType(1);
        this.messageMapper.insertSelective(message);
    }

    /**
     * 发送消息到指定广播队列
     *
     * @param channelId 消息频道
     * @param message   消息内容
     */
    public void sendToTopic(String channelId, SlMessage message) {
        this.messagingTemplate.convertAndSend("/topic/" + channelId, message);
        message.setType(2);
        this.messageMapper.insertSelective(message);
    }

}
