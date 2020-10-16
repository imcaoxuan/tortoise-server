package top.caoxuan.tortoiseserver.entity;

import com.alibaba.fastjson.JSON;

/**
 * @author CX
 * @version 1.0
 * @date 2020/10/14 16:21
 */
public class Message {

    private Long id;
    private Integer type;
    private String from;
    private String sessionId;
    private String content;
    private String roomId;

    public static class Type {
        public static final Integer AUTH = 0;
        public static final Integer MSG = 1;
    }

    public Message(Integer type, String from, String sessionId, String content, String roomId) {
        this.type = type;
        this.from = from;
        this.sessionId = sessionId;
        this.content = content;
        this.roomId = roomId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String toJSONString() {
        return JSON.toJSONString(this);
    }
}
