package com.songpo.searched.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.songpo.searched.entity.SlMessage;
import com.songpo.searched.mapper.SlMessageMapper;
import com.songpo.searched.typehandler.MessageTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 刘松坡
 */
@Slf4j
@Service
public class NotificationService {

    @Autowired
    private SlMessageMapper messageMapper;

    @Autowired
    private RabbitMessagingTemplate messagingTemplate;

    @Autowired
    private RabbitMqChannelCache channelCache;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    /**
     * 发送消息到指定单播队列
     *
     * @param sourceId 来源标识
     * @param targetId 目标标识
     * @param content  消息内容
     */
    void sendToQueue(String sourceId, String targetId, String content) {
        SlMessage message = new SlMessage();
        message.setSourceId(sourceId);
        message.setTargetId(targetId);
        message.setContent(content);
        message.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
        message.setType(MessageTypeEnum.QUEUE.getValue());
        this.messageMapper.insertSelective(message);

        // 如果频道不存在，则进行创建
        String channelName = "queue_" + targetId;
        if (!channelCache.hasKey(channelName)) {
            try {
                rabbitAdmin.declareQueue(new Queue(channelName));
            } catch (Exception e) {
                log.debug("频道[{}]已存在", channelName);
            }
        } else {
            channelCache.put(channelName, channelName);
        }

        // 发送单播消息
        this.messagingTemplate.convertAndSend(channelName, JSON.toJSONString(message));
    }

    /**
     * 发送消息到指定广播队列
     *
     * @param sourceId 来源标识
     * @param targetId 目标标识
     * @param content  消息内容
     */
    public void sendToTopic(String sourceId, String targetId, String content) {
        SlMessage message = new SlMessage();
        message.setSourceId(sourceId);
        message.setTargetId(targetId);
        message.setContent(content);
        message.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
        message.setType(MessageTypeEnum.TOPIC.getValue());
        this.messageMapper.insertSelective(message);

        // 如果频道不存在，则进行创建
        String channelName = "topic_" + targetId;
        if (!channelCache.hasKey(channelName)) {
            try {
                rabbitAdmin.declareExchange(new TopicExchange(channelName));
            } catch (Exception e) {
                log.debug("频道[{}]已存在", channelName);
            }
        } else {
            channelCache.put(channelName, channelName);
        }

        // 发送广播消息
        this.messagingTemplate.convertAndSend(channelName, JSON.toJSONString(message));
    }

}
