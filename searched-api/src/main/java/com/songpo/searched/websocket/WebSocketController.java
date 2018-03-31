package com.songpo.searched.websocket;

import com.songpo.searched.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * @author 刘松坡
 */
@Api(description = "消息发送管理")
@Controller
@MessageMapping("/api/common/v1/ws")
public class WebSocketController {

    @Autowired
    private NotificationService notificationService;

    @ApiOperation(value = "单播消息发送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceId", value = "来源标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "targetId", value = "目标标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "content", value = "消息内容", paramType = "form", required = true)
    })
    @MessageMapping("/queue/{sourceId}/{targetId}")
    public void sendToQueue(@DestinationVariable String sourceId, @DestinationVariable String targetId, String content) {
        this.notificationService.sendToQueue(sourceId, targetId, content);
    }

    @ApiOperation(value = "单播消息发送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceId", value = "来源标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "targetId", value = "目标标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "content", value = "消息内容", paramType = "form", required = true)
    })
    @MessageMapping("/topic/{sourceId}/{targetId}")
    public void sendToTopic(@DestinationVariable String sourceId, @DestinationVariable String targetId, String content) {
        this.notificationService.sendToQueue(sourceId, targetId, content);
    }
}
