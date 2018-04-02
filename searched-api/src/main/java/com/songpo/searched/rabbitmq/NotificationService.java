package com.songpo.searched.rabbitmq;

import com.songpo.searched.entity.SlMessage;
import com.songpo.searched.mapper.SlMessageMapper;
import com.songpo.searched.typehandler.MessageTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    /**
     * 发送消息到指定单播队列
     *
     * @param sourceId 来源标识
     * @param targetId 目标标识
     * @param content  消息内容
     */
    public void sendToQueue(String sourceId, String targetId, String content) {
        SlMessage message = new SlMessage();
        message.setSourceId(sourceId);
        message.setTargetId(targetId);
        message.setContent(content);
        message.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
        message.setType(MessageTypeEnum.QUEUE.getValue());
        this.messageMapper.insertSelective(message);

        // 发送单播消息
        this.messagingTemplate.convertAndSend("notificationQueue", message);
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

        // 发送广播消息
        this.messagingTemplate.convertAndSend("notificationTopic", message);
    }

    /**
     * 消息回调通知
     *
     * @param message 消息结构
     */
    @RabbitListener(queues = "notificationQueue")
    @RabbitHandler
    public void processQueue(SlMessage message) {
        log.debug("单播消息内容：{}", message);
    }

    /**
     * 消息回调通知
     *
     * @param message 消息结构
     */
    @RabbitListener(queues = "notificationTopic")
    @RabbitHandler
    public void processTopic(SlMessage message) {
        log.debug("广播消息内容：{}", message);
    }

}
