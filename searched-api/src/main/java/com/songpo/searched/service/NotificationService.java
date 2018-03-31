package com.songpo.searched.service;

import com.songpo.searched.entity.SlMessage;
import com.songpo.searched.mapper.SlMessageMapper;
import com.songpo.searched.typehandler.MessageTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
     * @param sourceId 来源标识
     * @param targetId 目标标识
     * @param content  消息内容
     */
    public void sendToQueue(String sourceId, String targetId, String content) {
        this.messagingTemplate.convertAndSend("/queue/" + targetId, content);
        SlMessage message = new SlMessage();
        message.setSourceId(sourceId);
        message.setTargetId(targetId);
        message.setContent(content);
        message.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
        message.setType(MessageTypeEnum.QUEUE.getValue());
        this.messageMapper.insertSelective(message);
    }

    /**
     * 发送消息到指定广播队列
     *
     * @param sourceId 来源标识
     * @param targetId 目标标识
     * @param content  消息内容
     */
    public void sendToTopic(String sourceId, String targetId, String content) {
        this.messagingTemplate.convertAndSend("/topic/" + targetId, content);
        SlMessage message = new SlMessage();
        message.setSourceId(sourceId);
        message.setTargetId(targetId);
        message.setContent(content);
        message.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
        message.setType(MessageTypeEnum.TOPIC.getValue());
        this.messageMapper.insertSelective(message);
    }

}
