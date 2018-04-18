//package com.songpo.searched.websocket;
//
//import com.songpo.searched.cache.UserCache;
//import com.songpo.searched.entity.SlUser;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
//import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
//
///**
// * @author 刘松坡
// */
////public class MyWebSocketHandlerDecoratorFactory implements WebSocketHandlerDecoratorFactory {
//
//    @Autowired
//    private UserCache userCache;
//
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    @Override
//    public WebSocketHandler decorate(final WebSocketHandler handler) {
//        return new WebSocketHandlerDecorator(handler) {
//            @Override
//            public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
//                // 客户端与服务器端建立连接后，此处记录谁上线了
//                String clientId = session.getPrincipal().getName();
//
//                // 从缓存查询用户信息
//                SlUser user = userCache.get(clientId);
//
//                log.info("online: " + clientId);
//
//                // 向用户所在的群发送该用户登录的消息
////                messagingTemplate.send("/topic/" + 群的ID, new GenericMessage<>("欢迎您登录系统"));
//
//                super.afterConnectionEstablished(session);
//            }
//
//            @Override
//            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
//                // 客户端与服务器端断开连接后，此处记录谁下线了
//                String clientId = session.getPrincipal().getName();
//
//                // 从缓存查询用户信息
//                SlUser user = userCache.get(clientId);
//
//                log.info("offline: " + clientId);
//
//                super.afterConnectionClosed(session, closeStatus);
//            }
//        };
//    }
//}
