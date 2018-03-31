package com.songpo.searched.websocket;

import com.songpo.searched.entity.SlMessage;
import com.songpo.searched.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 刘松坡
 */
@Api(description = "消息发送管理")
@Controller
@MessageMapping("/api/common/v2/msg")
public class MessageController {

    @Autowired
    private NotificationService notificationService;

    @ApiOperation(value = "单播消息发送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "channelId", value = "频道标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "targetId", value = "用户标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "content", value = "消息内容", paramType = "form", required = true)
    })
    @MessageMapping("queue")
    public void sendToQueue(@PathVariable String channelId, SlMessage message) {
        this.notificationService.sendToQueue(channelId, message);
    }

    @ApiOperation(value = "单播消息发送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "channelId", value = "频道标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "targetId", value = "群组标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "content", value = "消息内容", paramType = "form", required = true)
    })
    @MessageMapping("topic")
    public void sendToTopic(@PathVariable String channelId, SlMessage message) {
        this.notificationService.sendToQueue(channelId, message);
    }
}
