package com.tao.mqlibrary.mqsun.mq;


 

import com.example.utlis.o;
import com.tao.mqlibrary.mqsun.Message.MqMssage;
import com.tao.mqlibrary.mqsun.iml.IHandlerCreate;
import com.tao.mqlibrary.mqsun.iml.IMq;
import com.tao.os.handler.message.Handler;
import com.tao.os.handler.message.Looper;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MqRuan implements Runnable, IMq {
    String TAG = getClass().getSimpleName();
    MqHelper mqHelper;
    private Looper myLooper;
    private Handler runHandler;
    private MQClient mqttClient;
    IHandlerCreate iHandlerCreate;
    private Runnable autoConnectRun;
    private boolean autoconn = false;
    boolean handlepPrepare = false;
    boolean distory = false;
    private IMqttDeliveryToken mpublish;
    Map<IMqttDeliveryToken, List<MqMssage>> publishMap = new HashMap<>();
    Map<String, IMqttDeliveryToken> tokenMap = new HashMap<>();
    List<MqMssage> sendMessageList = new ArrayList<>();
    private boolean destory;


    public MqRuan(MqHelper mqHelper, IHandlerCreate iHandlerCreate) {
        this.mqHelper = mqHelper;
        this.iHandlerCreate = iHandlerCreate;
        autoConnectRun = new Runnable() {
            @Override
            public void run() {
                if (!autoconn)
                    return;
                reConnect();
            }
        };
    }

    @Override
    public void run() {
        try {
            Looper.prepare();
            myLooper = Looper.myLooper();
            runHandler = new Handler(myLooper);
            handlepPrepare = true;
            if (iHandlerCreate != null)
                iHandlerCreate.OnHandlerLooper(true);
            Looper.loop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (iHandlerCreate != null)
                iHandlerCreate.OnHandlerLooper(false);
        }
    }

    public void destory() {

        disconnect();
        postRun(new Runnable() {
            @Override
            public void run() {
                quit();
            }
        });
        distory = true;
    }

    public void quit() {
        if (myLooper != null)
            myLooper.quit();
        myLooper = null;
        runHandler = null;
    }

    public void createMq() throws Exception {

        if (mqHelper.build == null)
            throw new Exception("MQ parameter is null ");
        clearData();
        // 创建 mqtt数据持久化文件路径
        MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(mqHelper.build.getFilesDir());
        // Mqtt 客户端 1.服务端地址 Mqtt服务器地址(tcp://xxxx:1863)   2. 客户端id （唯一）
        mqttClient = new MQClient(mqHelper.build.getServerUrl(), mqHelper.build.clientid(), dataStore);
        mqttClient.setCallback(new MyMqCall());
    }

    private void clearData() {
        sendMessageList.clear();
        publishMap.clear();
        tokenMap.clear();
    }

    @Override
    public void send(final MqMssage mqMssage) throws Exception {
        postRun(new Runnable() {
            @Override
            public void run() {
//                o.e(TAG, "postMesage:" + mqMssage.toString());
                if (mqMssage == null || mqMssage.themeS == null)
                    return;
                for (String str : mqMssage.themeS) {
                    IMqttDeliveryToken token = null;
                    if (tokenMap.containsKey(str)) {
                        token = tokenMap.get(str);
                    }

                    MqMssage mssage = copyMessage(mqMssage);
                    MqttMessage mqttMessage = new MqttMessage();
                    mqttMessage.setPayload(mssage.message.getBytes());
                    mqttMessage.setId(token == null ? 2 : token.getMessageId() + 1);

                    mssage.themeS = new String[]{str};
                    mqttMessage.setQos(mssage.qos);
                    try {
                        if (mqttClient == null) {
                            mqHelper.build.getStatueCall().sendDataStatue(false, mssage);
                            mqHelper.build.getStatueCall().OnConnectStatueChange(false);
                            return;
                        }

                        token = mqttClient.mqublish(str, mqttMessage);
//                       o.e(TAG ,"token:" +token);
                        mssage.messageId = token.getMessageId();
                        sendMessageList.add(mssage);
//                        if (!tokenMap.containsKey(str)) {
//                              tokenMap.put(str,token);
//                        }
//
//                        if (publishMap.containsKey(token))
//                            publishMap.get(token).add(mssage);
//                        else {
//                            ArrayList<MqMssage> list = new ArrayList<>();
//                            list.add(mssage);
//                            publishMap.put(token,list);
//                        }
//                       o.e(TAG ,"mqublish 2 " + token.getMessageId());


                    } catch (MqttException e) {
                        e.printStackTrace();
                        mqHelper.build.getStatueCall().sendDataStatue(false, mssage);
                    }
                }
            }

            private MqMssage copyMessage(MqMssage mqMssage) {

                MqMssage mssage = new MqMssage();
                mssage.messageId = mqMssage.messageId;
                mssage.message = mqMssage.message;
                mssage.type = mqMssage.type;
                mssage.themeS = mqMssage.themeS;
                mssage.qos = mqMssage.qos;
                return mssage;
            }
        });
    }

    @Override
    public synchronized void connect() {
        if (destory) {
            o.d(TAG ,"mq Tag is destory ");
            return;
        }
        postRun(new Runnable() {
            @Override
            public void run() {

                autoconn = true;
                try {
                    if (mqHelper.connectIng) {
//                       o.e(TAG, " 正在连接。。");
                        return;
                    }
                    mqHelper.connectIng = true;
                    if (isConnect()) {
                        o.d(TAG ,"mq isaleardy connect ");
                        return;
                    }
                    createMq();
//                    o.e(TAG, "connect ");
                    // Mqtt 连接参数配置
                    MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
                    mqttConnectOptions.setConnectionTimeout(mqHelper.build.getConnectTimeout());
                    mqttConnectOptions.setUserName(mqHelper.build.getUserName());
                    mqttConnectOptions.setPassword(mqHelper.build.getPassword().toCharArray());
                    mqttConnectOptions.setCleanSession(true);
                    mqttConnectOptions.setKeepAliveInterval(mqHelper.build.getKeepAliveTime());
                    mqttClient.connectWithResult(mqttConnectOptions);
                    mqHelper.isConnect = true;
                    sub(mqHelper.build.topis());
//                    o.e(TAG, "connect success " + mqHelper.build.clientid() + " sub " + Arrays.toString(mqHelper.build.topis()));
                    mqHelper.build.OnConnectStatueChange(true);

                } catch (Exception e) {
                    e.printStackTrace();
                    o.e(TAG, " " + e.toString());
                    autoReconnect();
                } finally {
                    mqHelper.connectIng = false;
                }
            }
        });

    }

    private void autoReconnect() {
        if (!mqHelper.build.isAutoReconnect() && !autoconn && isConnect() || runHandler == null || distory)
            return;
        postDely(autoConnectRun, mqHelper.build.getAutoReconnectTime() * 1000);
    }

    private void postDely(Runnable runnable, int autoReconnectTime) {
        if (handlepPrepare && runHandler != null)
            runHandler.postDelayed(runnable, autoReconnectTime);
    }

    @Override
    public void disconnect() {
        try {
            if (runHandler != null)
                runHandler.removeCallbacks(autoConnectRun);
        } catch (Exception e) {
            e.printStackTrace();
        }
        autoconn = false;
        postRun(new Runnable() {
            @Override
            public void run() {
                if (mqttClient != null) {
//                    o.e(TAG, "disconnect:");
                    try {
                        mqttClient.disconnect();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    try {
                        mqttClient.close();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                    clearData();
                    mqttClient = null;
                    mqHelper.build.OnConnectStatueChange(false);
                }
            }
        });
    }

    @Override
    public synchronized boolean reConnect() {
        if (distory)
            return false;
        postRun(new Runnable() {
            @Override
            public void run() {
//                o.e(TAG, "reConnect");
                disconnect();
                connect();
            }
        });
        return true;
    }

    public boolean isConnect() {
        if (mqHelper.isConnect && mqttClient != null)
            return mqttClient.isConnected();
        else
            return false;
    }

    public void sub(final String[] themes) throws Exception {
        postRun(new Runnable() {
            @Override
            public void run() {
                if (!isConnect()) {
                    autoReconnect();
                    return;
                }
                try {
                    mqttClient.subscribe(themes);
                } catch (MqttException e) {
                    e.printStackTrace();
                    mqHelper.build.OnConnectStatueChange(false);
                }
            }
        });
    }

    public boolean isDestory() {
        return destory;
    }

    class MyMqCall implements MqttCallback {
        @Override
        public void connectionLost(Throwable cause) {
            o.e(TAG, "connectionLost " + cause.toString());
            // 连接断开
            mqHelper.isConnect = false;
            mqHelper.build.OnConnectStatueChange(false);
            autoReconnect();
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {

            // 收到消息
            MqMssage mqMessage = new MqMssage();
            try {
                mqMessage.message = getMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mqMessage.type = MqMssage.Type.receiver;
            mqMessage.messageId = message.getId();
            mqMessage.themeS = new String[]{topic};
            mqMessage.qos = message.getQos();
            mqHelper.build.OnMessage(mqMessage);
//           o.e(TAG,"mqMessageReceiver: " +mqMessage.toString());
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
//           o.e(TAG ," token " + token);
            // 消息发送成功
            mqHelper.build.sendDataStatue(true, getMessageByToken(token));

        }
    }

    private MqMssage getMessageByToken(IMqttDeliveryToken token) {

        for (MqMssage mssage : sendMessageList) {

            if (mssage == null)
                continue;
            if (mssage.messageId == token.getMessageId()) {

                sendMessageList.remove(mssage);
                return mssage;
            }
        }

        return null;
    }

    private MqMssage getMessageByid(IMqttDeliveryToken token) {
        List<MqMssage> mqMssages = publishMap.get(token);
        for (MqMssage mssage : mqMssages) {
            if (mssage == null)
                continue;
//           o.e(TAG ,"getMessageByid:"+mssage.toString() +"  token id "+  token.getMessageId());
            if (mssage.messageId == token.getMessageId()) {
                mqMssages.remove(mssage);
                publishMap.remove(token);
                return mssage;
            }
        }
        return null;
    }


    private String getMessage(MqttMessage message) {
        return new String(message.getPayload());
    }

    public void postRun(Runnable runable) {
        if (!handlepPrepare || runHandler == null)
            return;
        runHandler.post(runable);
    }


}
