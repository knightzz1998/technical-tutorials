package cn.knightzz.controller.websocket;

import cn.knightzz.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 王天赐
 * @title: WebsocketController
 * @projectName technical-tutorials
 * @description:
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-03-23 18:14
 */
@Component
@ServerEndpoint("/webSocket/{userName}")
@Slf4j
public class WebSocketController {

    private static final Map<String, Session> USERNAME_SESSION = new ConcurrentHashMap<>();
    private static final Map<String, String> SESSION_ID_USERNAME = new ConcurrentHashMap<>();
    private static final Map<String, String> SESSION_MESSAGE = new ConcurrentHashMap<>();

    @Resource
    private WebSocketService webSocketService;

    private static WebSocketController webSocketController;

    @PostConstruct
    public void refreshDate() {
        webSocketController = this;
        webSocketController.webSocketService = this.webSocketService;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userName") String userName) {
        log.debug("{} 连接成功!", userName);
        SESSION_ID_USERNAME.put(session.getId(), userName);
        USERNAME_SESSION.put(userName, session);
    }

    public void sendMessage(String message) {

        USERNAME_SESSION.forEach((username, session) -> {
            try {
                session.getBasicRemote().sendText( "hello " + username);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
