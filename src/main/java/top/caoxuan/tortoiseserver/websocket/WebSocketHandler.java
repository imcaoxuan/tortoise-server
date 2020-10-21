package top.caoxuan.tortoiseserver.websocket;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import top.caoxuan.tortoiseserver.entity.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CX
 * @date 2020/10/21 11:14
 */
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    public static final List<WebSocketSession> SESSION_LIST = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        SESSION_LIST.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        super.handleTextMessage(session, textMessage);
        logger.info(textMessage.getPayload());
        String payload = textMessage.getPayload();
        Message message;
        try {
            message = JSON.parseObject(payload, Message.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("消息格式转换失败");
            session.close(CloseStatus.NOT_ACCEPTABLE);
            return;
        }
        switch (message.getType()) {
            case Message.Type.TEXT:
                for (WebSocketSession webSocketSession :
                        SESSION_LIST) {
                    if (webSocketSession != session) {
                        webSocketSession.sendMessage(new TextMessage(payload));
                        logger.info("转发成功！messageId=" + message.getId());
                    }
                }
                break;
            case Message.Type.IMG:
                logger.warn("暂不支持图片");
                break;
            default:
                session.close(CloseStatus.POLICY_VIOLATION);
                logger.warn("未知的消息类型：" + message.getType());
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        SESSION_LIST.remove(session);
        logger.warn(session.getId() + "已断开连接");

    }
}
