package com.example.mqPush;

import com.example.demo.data.User;
import com.example.demo.service.UserService;
import com.example.utlis.o;
import com.google.gson.Gson;
import com.tao.mqlibrary.mqsun.Message.MqMssage;
import com.tao.mqlibrary.mqsun.iml.MqStatueCall;
import com.tao.mqlibrary.mqsun.mq.MqHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class Mqtt {
    static {
        try {
            mqHelper = new MqHelper.Build()
                    .config("tcp://", "60.205.182.166", "1883", 
                            "server"+new Random().nextInt(9999), "admin", "admin")
                    .setAutoReconnect(true)
                    .setKeepAliveTime(5 * 60)
                    .setAutoReconnectTime(1)
                    .setMqStatueCall(new MyMessageCall())
                    .sub(new String[]{"server01"})
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static MqHelper mqHelper;
    public static void sendMessage(Object obj, String[] toTheme) throws Exception {
        if (mqHelper == null || !mqHelper.isConnect() || toTheme.length<=0)
            return;
        MqMssage mqMssage = new MqMssage();
        String message =  new StringBuilder(new Gson().toJson(obj)).toString();
        mqMssage.message = message;
        mqMssage.type = MqMssage.Type.send;
        mqMssage.themeS = toTheme;
        mqMssage.messageId = MqHelper.getMsgId();
        mqHelper.send(mqMssage);
        o.e("mqtt send " +mqMssage.toString());
    }
    
  public static void sendMessage(Object obj, String toTheme) throws Exception {
        if (mqHelper == null || !mqHelper.isConnect())
            return;
        MqMssage mqMssage = new MqMssage();
        mqMssage.message = new Gson().toJson(obj);
        mqMssage.type = MqMssage.Type.send;
        mqMssage.themeS = new String[]{toTheme};
        mqMssage.messageId = MqHelper.getMsgId();
        mqHelper.send(mqMssage);
    }

    public static String[] getMessageTheme(UserService userService, MessageType plan) {
        List<User> userList = userService.selectAllExeitsUser();
        List<String> themes =new ArrayList<>();
        for (User user : userList) {
            if(user.status!=1)
                continue;
            themes.add(user.identifyId+"/"+ MessageType.TypeValue(plan));
        }
        return themes.toArray(new String[themes.size()]); 
    }


    public static class MyMessageCall implements MqStatueCall {
        @Override
        public void OnConnectStatueChange(boolean conn) {
            o.e("OnConnectStatueChange " + conn);
        }
        @Override
        public void sendDataStatue(boolean send, MqMssage message) {
            o.e("sendDataStatue " + send + " " + message.messageId);
        }
        @Override
        public void OnMessage(MqMssage mqMessage) {
            o.e("OnMessage " + mqMessage.message);
        }
    }

}
