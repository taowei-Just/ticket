package com.tao.mqlibrary.mqsun.iml;


import com.tao.mqlibrary.mqsun.Message.MqMssage;

public interface MqStatueCall {
    void  OnConnectStatueChange(boolean conn);
    void  sendDataStatue(boolean send, MqMssage message);
    void  OnMessage(MqMssage mqMessage);

    public  enum ConnectStatue{
        conn,disconn,
    }
}
