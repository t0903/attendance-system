package com.lyzyxy.attendance.base;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PushMessage {
    //登录用户编号
    private String clientId;
    //消息类型
    private int type;
    //推送内容
    private String content;
}
