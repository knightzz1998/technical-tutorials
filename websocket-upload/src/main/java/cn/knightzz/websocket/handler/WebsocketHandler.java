package cn.knightzz.websocket.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 王天赐
 * @title: WebsocketHandler
 * @projectName technical-tutorials
 * @description:
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-03-23 18:05
 */
@Slf4j
@Component
public class WebsocketHandler implements WebSocketHandler {

    /**
     * 存储连接session
     */
    private static final Map<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();

    /**
     * 连接建立回调
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("连接成功" + session.getId());
        SESSIONS.put(session.getId(), session);
        System.out.println("当前在线人数：" + SESSIONS.size());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("{} 接收到消息 ==> {}", session.getId(), message.getPayload().toString());
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1000);

        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("{} 连接出错", session.getId());
        if (!session.isOpen()) {
            SESSIONS.remove(session.getId());
            session.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.error("{} 关闭连接", session.getId());
        if (!session.isOpen()) {
            SESSIONS.remove(session.getId());
            System.out.println("当前在线人数：" + SESSIONS.size());
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
