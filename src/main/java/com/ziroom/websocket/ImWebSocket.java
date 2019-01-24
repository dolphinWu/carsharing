package com.ziroom.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ziroom.utils.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author wudaoping
 * @Date 19-1-23 下午8:15
 */
@Slf4j
@ServerEndpoint("/im/{userId}")
@Component
public class ImWebSocket {

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 使用map对象，便于根据userId来获取对应的WebSocket
     */
    private static ConcurrentHashMap<String, ImWebSocket> websocketList = new ConcurrentHashMap<>();

    /**
     * 接收sid
     */
    private String userId = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        websocketList.put(userId, this);
        log.info("websocketList->{}", websocketList);
        addOnlineCount();           //在线数加1
        log.info("有用户加入:" + userId + ",当前在线人数为" + getOnlineCount());
        this.userId = userId;
        try {
            sendMessage(JSON.toJSONString(APIResponse.success("连接成功")));
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (websocketList.get(this.userId) != null) {
            websocketList.remove(this.userId);
            //在线数减1
            subOnlineCount();
            log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + userId + "的信息:" + message);
        if (StringUtils.isNotBlank(message)) {
            JSONArray list = JSONArray.parseArray(message);
            for (int i = 0; i < list.size(); i++) {
                try {
                    //解析发送的报文
                    JSONObject object = list.getJSONObject(i);
                    String toUserId = object.getString("toUserId");
                    String contentText = object.getString("contentText");
                    object.put("fromUserId", this.userId);
                    object.put("contentText", contentText);
                    //传送给对应用户的websocket
                    if (StringUtils.isNotBlank(toUserId) && StringUtils.isNotBlank(contentText)) {
                        ImWebSocket socketTo = websocketList.get(toUserId);
                        //需要进行转换，userId
                        if (socketTo != null) {
                            socketTo.sendMessage(JSON.toJSONString(APIResponse.success(object)), socketTo);
                            //此处可以放置相关业务代码，例如存储到数据库
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message, ImWebSocket imWebSocket) throws IOException {
        imWebSocket.getSession().getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    /*public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("推送消息到窗口"+userId+"，推送内容:"+message);
        for (ImController item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if(userId==null) {
                    item.sendMessage(message);
                }else if(item.userId.equals(userId)){
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }*/
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        ImWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        ImWebSocket.onlineCount--;
    }


    public Session getSession() {
        return session;
    }
}
