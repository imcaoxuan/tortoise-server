package top.caoxuan.tortoiseserver.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import top.caoxuan.tortoiseserver.config.WebSecurityConfig;
import top.caoxuan.tortoiseserver.entity.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CX
 * @version 1.0
 * @date 2020/10/14 14:59
 */
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    private static final List<WebSocketSession> SESSION_LIST = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        super.handleTextMessage(session,textMessage);
        logger.warn(textMessage.getPayload());
        String payload = textMessage.getPayload();
        Message message;
        try {
            message = JSON.parseObject(payload, Message.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("消息格式转换失败");
            return;
        }
        if (Message.Type.MSG.equals(message.getType())) {
            if (WebSecurityConfig.uuid.toString().equals(message.getUuid())) {
                /*List<WebSocketSession> sessionList = SESSION_LIST;
                sessionList.remove(session);
                for (WebSocketSession webSocketSession : sessionList) {
                    webSocketSession.sendMessage(new TextMessage(payload));
                }*/
                for (WebSocketSession webSocketSession:
                        SESSION_LIST) {
                    if (webSocketSession!= session) {
                        webSocketSession.sendMessage(new TextMessage(payload));
                    }
                }
            } else {
                session.close(CloseStatus.POLICY_VIOLATION);
                logger.warn("会话 " + session.getId() + "由于认证失败而被强制关闭");
            }
        } else if (Message.Type.AUTH.equals(message.getType())) {
            if (WebSecurityConfig.uuid.toString().equals(message.getUuid())) {
                SESSION_LIST.add(session);
                logger.info(session.getId() + "已加入");
            } else {
                session.close(CloseStatus.POLICY_VIOLATION);
                logger.warn(session.getId() + "由于认证失败而被拒绝加入");
            }
        } else {
            session.close(CloseStatus.POLICY_VIOLATION);
            logger.warn("未知的消息类型：" + message.getType());
        }

    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        SESSION_LIST.remove(session);
        logger.warn(session.getId() + "已断开连接");
    }
}
