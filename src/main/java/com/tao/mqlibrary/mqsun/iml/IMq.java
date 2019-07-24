package com.tao.mqlibrary.mqsun.iml;


import com.tao.mqlibrary.mqsun.Message.MqMssage;

public interface IMq {

    void send(MqMssage mssage) throws Exception;

    void connect() throws Exception;

    void disconnect() throws Exception;

    boolean reConnect() throws Exception;

}
