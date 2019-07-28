package com.lyzyxy.attendance.service;

import com.lyzyxy.attendance.base.PushMessage;

public interface ISocketIOService {
    //推送的事件
    public static final String INFO = "info";

    // 启动服务
    void start() throws Exception;

    // 停止服务
    void stop();

    // 推送信息
    void pushMessageToUser(PushMessage pushMessage);
}
