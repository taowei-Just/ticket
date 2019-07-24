package com.tao.mqlibrary.mqsun.Message;

import java.io.Serializable;
import java.util.Arrays;

public class MqMssage implements Serializable  {

    public Type type;
    public String message;
    public String[] themeS;
    public int messageId =-1;
    public int qos =1;

    public enum Type implements Serializable {
        send, receiver
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();

    }

    @Override
    public String toString() {
        return "MqMssage{" +
                "type=" + type +
                ", message='" + message + '\'' +
                ", themeS=" + Arrays.toString(themeS) +
                ", messageId=" + messageId +
                ", qos=" + qos +
                '}';
    }
}
