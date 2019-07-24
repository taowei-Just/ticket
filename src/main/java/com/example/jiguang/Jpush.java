package com.example.jiguang;

import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.example.utlis.o;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Jpush {

    public static String masterSecret = "789a8b55dfc994438bd454b3";
    public static String appKey = "dae9063656df96bf56cf63d2";
    public static PushClient jpushClient = new PushClient(masterSecret, appKey);

    public static void pushAllMessage(String str) {
        try {
            str  = "time : "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())) +"   \n" +str;
            jpushClient.sendPush( getPushPayload(str));
        } catch ( Exception e) {
            o.d(e.toString());
        }
    }

    public static PushPayload getPushPayload(String message) {
        return (new PushPayload.Builder())
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.alert(message))
                .build();
    }

    public static void main(String[] args) {
        pushAllMessage("test");
    }
}
