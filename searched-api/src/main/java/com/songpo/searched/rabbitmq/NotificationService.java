package com.songpo.searched.rabbitmq;

import com.songpo.searched.entity.SlMessage;
import com.songpo.searched.mapper.SlMessageMapper;
import com.songpo.searched.typehandler.MessageChannelTypeEnum;
import com.songpo.searched.typehandler.MessageTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 刘松坡
 */
@Service
public class NotificationService {

    public static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private SlMessageMapper messageMapper;

    @Autowired
    private RabbitMessagingTemplate messagingTemplate;

    @Autowired
    private RabbitMqChannelCache channelCache;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private Environment env;

    /**
     * 发送消息到指定单播队列
     *  @param sourceId 来源标识
     * @param targetId 目标标识
     * @param content  消息内容
     * @param type  消息类型
     */
    public void sendToQueue(String sourceId, String targetId, String content, MessageTypeEnum type) {
        log.debug("发送单播消息，sourceId = [" + sourceId + "], targetId = [" + targetId + "], content = [" + content + "], type = [" + type + "]");
        SlMessage message = new SlMessage();
        message.setSourceId(sourceId);
        message.setTargetId(targetId);
        message.setContent(content);
        message.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
        message.setChannelType(MessageChannelTypeEnum.QUEUE.getValue());
        message.setMessageType(type.getValue());
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
    public void sendToTopic(String sourceId, String targetId, String content, MessageTypeEnum type) {
        log.debug("发送广播消息，sourceId = [" + sourceId + "], targetId = [" + targetId + "], content = [" + content + "], type = [" + type + "]");
        SlMessage message = new SlMessage();
        message.setSourceId(sourceId);
        message.setTargetId(targetId);
        message.setContent(content);
        message.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
        message.setChannelType(MessageChannelTypeEnum.TOPIC.getValue());
        message.setMessageType(type.getValue());
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
    public void sendGlobalMessage(String content, MessageTypeEnum type) {
        log.debug("发送全局消息，content = [" + content + "], type = [" + type + "]");
        SlMessage message = new SlMessage();
        message.setContent(content);
        message.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
        message.setChannelType(MessageChannelTypeEnum.TOPIC.getValue());
        message.setMessageType(type.getValue());
        this.messageMapper.insertSelective(message);

        // 如果频道不存在，则进行创建
        String channelName = env.getProperty("spring.rabbitmq.channelname");

        // 发送广播消息
        this.messagingTemplate.convertAndSend(channelName, "test", content);
    }

    /**
     * 检测频道是否存在
     *
     * @param channelName 频道名称
     */
    private void checkChannelName(String channelName) {
        if (!channelCache.hasKey(channelName)) {
            try {
                rabbitAdmin.declareQueue(new Queue(channelName));
            } catch (Exception e) {
                log.debug("频道[{}]已存在", channelName);
            }
        } else {
            channelCache.put(channelName, channelName);
        }
    }

}
