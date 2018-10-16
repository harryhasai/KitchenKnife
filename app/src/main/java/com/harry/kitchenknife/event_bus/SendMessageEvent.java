package com.harry.kitchenknife.event_bus;

/**
 * Created by Harry on 2018/10/15.
 * 用于在长连接里, 接收到服务器发送的消息, 告诉某一页面
 */
public class SendMessageEvent {

    private String message;

    public SendMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
