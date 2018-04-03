package com.songpo.searched.rabbitmq;

import com.songpo.searched.domain.BusinessMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘松坡
 */
@Slf4j
@RestController
@RequestMapping("/api/common/v1/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @ApiOperation(value = "单播消息发送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceId", value = "来源标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "targetId", value = "目标标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "content", value = "消息内容", paramType = "form", required = true)
    })
    @RequestMapping("/queue")
    public BusinessMessage<Void> sendToQueue(String sourceId, String targetId, String content) {
        log.debug("单播消息发送，来源标识：{}，目标标识：{}，消息内容：{}", sourceId, targetId, content);
        BusinessMessage<Void> message = new BusinessMessage<>();
        if (StringUtils.isBlank(sourceId)) {
            message.setMsg("来源标识为空");
        } else if (StringUtils.isBlank(targetId)) {
            message.setMsg("目标标识为空");
        } else if (StringUtils.isBlank(content)) {
            message.setMsg("消息内容为空");
        } else {
            try {
                this.notificationService.sendToQueue(sourceId, targetId, content);
                message.setSuccess(true);
            } catch (Exception e) {
                log.error("单播消息发送，", e);
                message.setMsg("单播消息发送，请重试");
            }
        }
        return message;
    }

    @ApiOperation(value = "广播消息发送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceId", value = "来源标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "targetId", value = "目标标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "content", value = "消息内容", paramType = "form", required = true)
    })
    @RequestMapping("/topic")
    public BusinessMessage<Void> sendToTopic(String sourceId, String targetId, String content) {
        log.debug("广播消息发送，来源标识：{}，目标标识：{}，内容：{}", sourceId, targetId, content);
        BusinessMessage<Void> message = new BusinessMessage<>();
        if (StringUtils.isBlank(sourceId)) {
            message.setMsg("来源标识为空");
        } else if (StringUtils.isBlank(targetId)) {
            message.setMsg("目标标识为空");
        } else if (StringUtils.isBlank(content)) {
            message.setMsg("消息内容为空");
        } else {
            try {
                this.notificationService.sendToTopic(sourceId, targetId, content);
                message.setSuccess(true);
            } catch (Exception e) {
                log.error("单播消息发送，", e);
                message.setMsg("单播消息发送，请重试");
            }
        }
        return message;
    }
}
