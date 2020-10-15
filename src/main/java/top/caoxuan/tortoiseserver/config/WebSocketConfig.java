package top.caoxuan.tortoiseserver.config;

import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import top.caoxuan.tortoiseserver.controller.WebSocketHandler;

/**
 * @author CX
 * @version 1.0
 * @date 2020/10/14 15:01
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(echoWebSocketHandler(), "/echo");
        //webSocketHandlerRegistry.addHandler(echoWebSocketHandler(), "/echo_js").withSockJS();
    }

    @Bean
    public WebSocketHandler echoWebSocketHandler(){
        return new WebSocketHandler();
    }
}
