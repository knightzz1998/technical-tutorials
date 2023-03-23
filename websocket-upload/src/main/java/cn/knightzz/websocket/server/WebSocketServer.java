package cn.knightzz.websocket.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 王天赐
 * @title: WebSocketServer
 * @projectName technical-tutorials
 * @description:
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-03-23 18:24
 */
@ServerEndpoint("/webSocket/{uid}")
@Component
@Slf4j
public class WebSocketServer {

    private static final AtomicInteger ONLINE_NUM = new AtomicInteger(0);

    private static final Map<String,Session> SESSION_POOLS = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "uid") String uid) throws IOException {
        SESSION_POOLS.put(uid,session);
        ONLINE_NUM.incrementAndGet();
        // 发送当前在线人数
        session.getBasicRemote().sendText("当前在线人数 : " + ONLINE_NUM.toString());
    }

    @OnClose
    public void onOpen(Session session) {
        log.debug("close ...");
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        log.debug("{} 发送 : {} " , session.getId(), message);
    }
}
