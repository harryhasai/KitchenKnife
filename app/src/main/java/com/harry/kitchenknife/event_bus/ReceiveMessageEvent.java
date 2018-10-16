package com.harry.kitchenknife.event_bus;

/**
 * Created by Harry on 2018/10/15.
 * 用于接收外部消息, 以便于长连接里发送消息到服务器
 */
public class ReceiveMessageEvent {

    private String message;

    public ReceiveMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
