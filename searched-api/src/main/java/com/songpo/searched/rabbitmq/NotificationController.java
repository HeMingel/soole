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
            @ApiImplicitParam(name = "content", value = "消息内容", paramType = "form", required = true),
            @ApiImplicitParam(name = "type", value = "消息类型" +
                    "1 系统通知(通用的)，\n" +
                    "21 普通商品购买通知，\n" +
                    "22 人气拼团商品购买通知，\n" +
                    "23 预售商品购买通知，\n" +
                    "24 豆赚商品购买通知，\n" +
                    "25 消费返利商品购买通知，\n" +
                    "26 1元购商品购买通知，\n" +
                    "31 无活动商品购买通知，\n" +
                    "32 新人专享商品购买通知，\n" +
                    "33 推荐奖励购买通知，\n" +
                    "34 限时秒杀购买通知", paramType = "form", required = true)
    })
    @RequestMapping("/queue")
    public BusinessMessage<Void> sendToQueue(String sourceId, String targetId, String content, Integer type) {
        log.debug("单播消息发送，来源标识：{}，目标标识：{}，消息内容：{}，消息类型：{}", sourceId, targetId, content, type);
        BusinessMessage<Void> message = new BusinessMessage<>();
        if (StringUtils.isBlank(sourceId)) {
            message.setMsg("来源标识为空");
        } else if (StringUtils.isBlank(targetId)) {
            message.setMsg("目标标识为空");
        } else if (StringUtils.isBlank(content)) {
            message.setMsg("消息内容为空");
        } else if (null == type) {
            message.setMsg("消息类型为空");
        } else {
            try {
                this.notificationService.sendToQueue(sourceId, targetId, content, type);
                message.setSuccess(true);
            } catch (Exception e) {
                log.error("单播消息发送失败，", e);
                message.setMsg("单播消息发送失败，请重试");
            }
        }
        return message;
    }

    @ApiOperation(value = "广播消息发送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceId", value = "来源标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "targetId", value = "目标标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "content", value = "消息内容", paramType = "form", required = true),
            @ApiImplicitParam(name = "type", value = "消息类型" +
                    "1 系统通知(通用的)，\n" +
                    "21 普通商品购买通知，\n" +
                    "22 人气拼团商品购买通知，\n" +
                    "23 预售商品购买通知，\n" +
                    "24 豆赚商品购买通知，\n" +
                    "25 消费返利商品购买通知，\n" +
                    "26 1元购商品购买通知，\n" +
                    "31 无活动商品购买通知，\n" +
                    "32 新人专享商品购买通知，\n" +
                    "33 推荐奖励购买通知，\n" +
                    "34 限时秒杀购买通知", paramType = "form", required = true)
    })
    @RequestMapping("/topic")
    public BusinessMessage<Void> sendToTopic(String sourceId, String targetId, String content, Integer type) {
        log.debug("广播消息发送，来源标识：{}，目标标识：{}，内容：{}，消息类型：{}", sourceId, targetId, content, type);
        BusinessMessage<Void> message = new BusinessMessage<>();
        if (StringUtils.isBlank(sourceId)) {
            message.setMsg("来源标识为空");
        } else if (StringUtils.isBlank(targetId)) {
            message.setMsg("目标标识为空");
        } else if (StringUtils.isBlank(content)) {
            message.setMsg("消息内容为空");
        } else if (null == type) {
            message.setMsg("消息类型为空");
        } else {
            try {
                this.notificationService.sendToTopic(sourceId, targetId, content, type);
                message.setSuccess(true);
            } catch (Exception e) {
                log.error("广播消息发送失败，", e);
                message.setMsg("广播消息发送失败，请重试");
            }
        }
        return message;
    }

    @ApiOperation(value = "全局消息发送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "消息内容", paramType = "form", required = true),
            @ApiImplicitParam(name = "type", value = "消息类型", paramType = "form", required = true)
    })
    @RequestMapping("/global")
    public BusinessMessage<Void> sendGlobalMessage(String content, Integer type) {
        log.debug("全局消息发送，内容：{}，类型：{}", content, type);
        BusinessMessage<Void> message = new BusinessMessage<>();
        if (StringUtils.isBlank(content)) {
            message.setMsg("消息内容为空");
        } else if (null == type) {
            message.setMsg("消息类型为空");
        } else {
            try {
                this.notificationService.sendGlobalMessage(content, type);
                message.setSuccess(true);
            } catch (Exception e) {
                log.error("全局消息发送失败，", e);
                message.setMsg("全局消息发送失败，请重试");
            }
        }
        return message;
    }
}
