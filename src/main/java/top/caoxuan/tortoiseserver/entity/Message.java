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

    public static class Type {
        public static final int AUTH = 0;
        public static final int TEXT = 1;
        public static final int IMG = 2;
    }

    public Message(Long id, Integer type, String from, String content) {
        this.id = id;
        this.type = type;
        this.from = from;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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



    public String toJSONString() {
        return JSON.toJSONString(this);
    }
}
