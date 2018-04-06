package com.songpo.searched.rabbitmq;

import com.songpo.searched.entity.SlMessage;
import com.songpo.searched.mapper.SlMessageMapper;
import com.songpo.searched.typehandler.MessageChannelTypeEnum;
import com.songpo.searched.typehandler.MessageTypeEnum;
import lombok.extern.slf4j.Slf4j;
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
     * @param type  消息类型
     */
    void sendToQueue(String sourceId, String targetId, String content, Integer type) {
        SlMessage message = new SlMessage();
        message.setSourceId(sourceId);
        message.setTargetId(targetId);
        message.setContent(content);
        message.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
        message.setChannelType(MessageChannelTypeEnum.QUEUE.getValue());
        message.setMessageType(type);
        this.messageMapper.insertSelective(message);

        // 如果频道不存在，则进行创建
        String channelName = "queue_" + targetId;
        checkChannelName(channelName);

        // 发送单播消息
        this.messagingTemplate.convertAndSend(channelName, content);
    }

    /**
     * 发送消息到指定广播队列
     *
     * @param sourceId 来源标识
     * @param targetId 目标标识
     * @param content  消息内容
     * @param type  消息类型
     */
    public void sendToTopic(String sourceId, String targetId, String content, Integer type) {
        SlMessage message = new SlMessage();
        message.setSourceId(sourceId);
        message.setTargetId(targetId);
        message.setContent(content);
        message.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
        message.setChannelType(MessageChannelTypeEnum.TOPIC.getValue());
        message.setMessageType(type);
        this.messageMapper.insertSelective(message);

        // 如果频道不存在，则进行创建
        String channelName = "topic_" + targetId;
        checkChannelName(channelName);

        // 发送广播消息
        this.messagingTemplate.convertAndSend(channelName, content);
    }

    /**
     * 发送全局通知
     *
     * @param content 消息内容
     */
    public void sendGlobalMessage(String content, Integer type) {
        SlMessage message = new SlMessage();
        message.setContent(content);
        message.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
        message.setChannelType(MessageChannelTypeEnum.TOPIC.getValue());
        message.setMessageType(MessageTypeEnum.SYSTEM.getValue());
        this.messageMapper.insertSelective(message);

        // 如果频道不存在，则进行创建
        String channelName = "topic_globalMessage";

        checkChannelName(channelName);

        // 发送广播消息
        this.messagingTemplate.convertAndSend("topic_globalMessage", content);
    }

    /**
     * 检测频道是否存在
     *
     * @param channelName 频道名称
     */
    private void checkChannelName(String channelName) {
        if (!channelCache.hasKey(channelName)) {
            try {
                rabbitAdmin.declareExchange(new TopicExchange(channelName));
            } catch (Exception e) {
                log.debug("频道[{}]已存在", channelName);
            }
        } else {
            channelCache.put(channelName, channelName);
        }
    }

}
