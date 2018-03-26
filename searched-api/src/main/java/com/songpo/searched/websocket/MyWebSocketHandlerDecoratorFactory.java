package com.songpo.searched.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

/**
 * @author 刘松坡
 */
@Slf4j
@Component
public class MyWebSocketHandlerDecoratorFactory implements WebSocketHandlerDecoratorFactory {

    @Override
    public WebSocketHandler decorate(final WebSocketHandler handler) {
        return new WebSocketHandlerDecorator(handler) {
            @Override
            public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
                // 客户端与服务器端建立连接后，此处记录谁上线了
                String username = session.getPrincipal().getName();
                log.info("online: " + username);
                super.afterConnectionEstablished(session);
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                // 客户端与服务器端断开连接后，此处记录谁下线了
                String username = session.getPrincipal().getName();
                log.info("offline: " + username);
                super.afterConnectionClosed(session, closeStatus);
            }
        };
    }
}
