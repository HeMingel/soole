package com.songpo.searched.service;

import com.songpo.searched.entity.*;

import java.util.Date;

/**
 * 消息中心-服务层
 */
public interface NotificationCenterService {
    /**
     * 物流发货通知
     * @param expressNo
     * @param noticedUser 被通知人
     */
    public void deliveryNotice(String expressNo,SlUser noticedUser);

    /**
     * 物流签收通知
     * @param expressNo
     * @param noticedUser 被通知人
     */
    public void signInNotice(String expressNo,SlUser noticedUser);

    /**
     * 拼团成功通知
     * @param orderId
     * @param noticedUser
     */
    public void spellGroupSuccessNotice(String orderId,SlUser noticedUser);

    /**
     * 拼团失效通知
     * @param order
     * @param noticedUser 被通知人
     */
    public void spellGroupInvalidNotice(String orderId,SlUser noticedUser);

    /**
     * 豆花费通知
     * @param beanCount
     * @param noticeUser 被通知人
     */
    public void costBeanNotice(Integer beanCount,SlUser noticedUser);

    /**
     * 豆收入通知
     * @param beanCount
     * @param noticedUser 被通知人
     */
    public void inComeBeanNotice(Integer beanCount,SlUser noticedUser);


    /**
     * 账户余额消费通知
     * @param money
     * @param noticedUser 被通知人
     */
    public void accountMoneyCostNotice(double money,SlUser noticedUser);

    /**
     * 账户余额收入通知
     * @param money
     * @param noticedUser 被通知人
     */
    public void accountMoneyIncomeNotice(double money,SlUser noticedUser);

    /**
     * 系统公告
     * @param startTime
     * @param endTime
     * @param noticedUser
     */
    public void systemAnnouncement(Date startTime,Date endTime,SlUser noticedUser);

    /**
     * 是否有未读
     */
    public boolean ishasNotReadNotice();

    /**
     * 是否有未读物流通知
     * @return
     */
    public boolean ishasNotReadExpressNotice();

    /**
     * 是否有未读账户通知
     * @return
     */
    public boolean ishasNotReadAccountNotice();

    /**
     * 是否有未读拼团通知
     * @return
     */
    public boolean ishasNotReadSpellGroupNotice();

    /**
     * 是否有未读系统通知
     * @return
     */
    public boolean ishasNotReadSystemNotice();

    /**
     * 新增一个物流通知
     * @param notice
     */
    public void addExpressNotice(NotificationContent notice);

    /**
     * 新增一个账户通知
     * @param notice
     */
    public void addAccountNotice(NotificationContent notice);

    /**
     * 新增一个拼团通知
     * @param notice
     */
    public void addSpellGroupNotice(NotificationContent notice);

    /**
     * 新增一个系统通知
     * @param notice
     */
    public void addSystemNotice(NotificationContent notice);
}
