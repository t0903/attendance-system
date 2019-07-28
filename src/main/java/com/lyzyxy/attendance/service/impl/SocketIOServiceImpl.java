package com.lyzyxy.attendance.service.impl;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.lyzyxy.attendance.base.PushMessage;
import com.lyzyxy.attendance.controller.UserController;
import com.lyzyxy.attendance.service.ISocketIOService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service(value = "socketIOService")
public class SocketIOServiceImpl implements ISocketIOService {
    // 用来存已连接的客户端
    private static Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(SocketIOServiceImpl.class);

    @Autowired
    private SocketIOServer socketIOServer;

    /**
     * Spring IoC容器创建之后，在加载SocketIOServiceImpl Bean之后启动
     * @throws Exception
     */
    @PostConstruct
    private void autoStartup() throws Exception {
        start();
    }

    /**
     * Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
     * @throws Exception
     */
    @PreDestroy
    private void autoStop() throws Exception  {
        stop();
    }

    @Override
    public void start() {
        // 监听客户端连接
        socketIOServer.addConnectListener(client -> {
            String clientId = getParamsByClient(client);
            if (clientId != null) {
                clientMap.put(clientId, client);
            }
        });

        // 监听客户端断开连接
        socketIOServer.addDisconnectListener(client -> {
            String clientId = getParamsByClient(client);
            if (clientId != null) {
                clientMap.remove(clientId);
                client.disconnect();
            }
        });

        // 处理自定义的事件，与连接监听类似
        socketIOServer.addEventListener(INFO, PushMessage.class, (client, data, ackSender) -> {
            logger.info(data.getClientId() + ":"+data.getContent());


        });
        socketIOServer.start();
    }

    @Override
    public void stop() {
        if (socketIOServer != null) {
            socketIOServer.stop();
            socketIOServer = null;
        }
    }

    @Override
    public void pushMessageToUser(PushMessage pushMessage) {
        String clientId = pushMessage.getClientId();
        if (StringUtils.isNotBlank(clientId)) {
            SocketIOClient client = clientMap.get(clientId);
            if (client != null)
                client.sendEvent(INFO, pushMessage);
        }
    }

    /**
     * 此方法为获取client连接中的参数，可根据需求更改
     * @param client
     * @return
     */
    private String getParamsByClient(SocketIOClient client) {
        // 从请求的连接中拿出参数（这里的loginUserNum必须是唯一标识）
        Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
        List<String> list = params.get("client");
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
