package com.songpo.searched.service.impl;

import com.songpo.searched.entity.NotificationContent;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.service.NotificationCenterService;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service("notificationCenterService")
public class NotificatioCenterCommonService implements NotificationCenterService {
    @Override
    public void deliveryNotice(String expressNo, SlUser noticedUser) {

    }

    @Override
    public void signInNotice(String expressNo, SlUser noticedUser) {

    }

    @Override
    public void spellGroupSuccessNotice(String orderId, SlUser noticedUser) {

    }

    @Override
    public void spellGroupInvalidNotice(String orderId, SlUser noticedUser) {

    }

    @Override
    public void costBeanNotice(Integer beanCount, SlUser noticedUser) {

    }

    @Override
    public void inComeBeanNotice(Integer beanCount, SlUser noticedUser) {

    }

    @Override
    public void accountMoneyCostNotice(double money, SlUser noticedUser) {

    }

    @Override
    public void accountMoneyIncomeNotice(double money, SlUser noticedUser) {

    }

    @Override
    public void systemAnnouncement(Date startTime, Date endTime, SlUser noticedUser) {

    }

    @Override
    public boolean ishasNotReadNotice() {
        return false;
    }

    @Override
    public boolean ishasNotReadExpressNotice() {
        return false;
    }

    @Override
    public boolean ishasNotReadAccountNotice() {
        return false;
    }

    @Override
    public boolean ishasNotReadSpellGroupNotice() {
        return false;
    }

    @Override
    public boolean ishasNotReadSystemNotice() {
        return false;
    }

    @Override
    public void addExpressNotice(NotificationContent notice) {

    }

    @Override
    public void addAccountNotice(NotificationContent notice) {

    }

    @Override
    public void addSpellGroupNotice(NotificationContent notice) {

    }

    @Override
    public void addSystemNotice(NotificationContent notice) {

    }
}
