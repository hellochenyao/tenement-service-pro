package com.kanata.core.common.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/im/{userId}")
@Component
@Slf4j
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //旧：concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //private static CopyOnWriteArraySet<ImController> webSocketSet = new CopyOnWriteArraySet<ImController>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //新：使用map对象，便于根据userId来获取对应的WebSocket
    private static ConcurrentHashMap<String, WebSocketServer> websocketList = new ConcurrentHashMap<>();
    //接收sid
    private String userId="";

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        websocketList.put(userId,this);
        log.info("websocketList->"+JSON.toJSONString(websocketList));
        //webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("有新窗口开始监听:"+userId+",当前在线人数为" + getOnlineCount());
        this.userId=userId;
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(websocketList.get(this.userId)!=null){
            websocketList.remove(this.userId);
            //webSocketSet.remove(this);  //从set中删除
            subOnlineCount();           //在线数减1
            log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口"+userId+"的信息:"+message);
    }


    /**
     *
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


    public static void sendInfoToAll(String type){
        log.info("推送消息给所有人,推送内容："+type);
        if(!StringUtils.isEmpty(type)||type!=null){
            Iterator<Map.Entry<String,WebSocketServer>> wbIterator = websocketList.entrySet().iterator();
            JSONObject response = new JSONObject();
            response.put("code",type);
            while(wbIterator.hasNext()){
                Map.Entry<String,WebSocketServer> entry = wbIterator.next();
                WebSocketServer webSocket = entry.getValue();
                try {
                    webSocket.sendMessage(JSONObject.toJSONString(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void sendMessage(String content,int toUserid){
        String toUseridStr = String.valueOf(toUserid);
        WebSocketServer receiveServer = websocketList.get(toUseridStr);
        if(receiveServer!=null){
            try {
                receiveServer.sendMessage(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 群发自定义消息
     * */
    public static void sendInfo(Integer msgId,String type,Integer toUserid, Integer userId) {
        log.info("推送消息到窗口"+userId+"，推送内容:"+type);
        String toUserId = String.valueOf(toUserid);
        String sendUserId = String.valueOf(userId);
        if(StringUtils.isNotBlank(toUserId)&&StringUtils.isNotBlank(type)){
            WebSocketServer receivetx=websocketList.get(toUserId);
            WebSocketServer sendtx=websocketList.get(sendUserId);//发消息的人
            JSONObject response = new JSONObject();
            response.put("code",type);
            if(receivetx!=null){
                response.put("toUser",toUserId);
                try {
                    receivetx.sendMessage(JSONObject.toJSONString(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(msgId!=null){//聊天的才有消息id
                response.put("id",msgId);
            }
            if(sendtx!=null){
                try {
                    sendtx.sendMessage(JSONObject.toJSONString(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
