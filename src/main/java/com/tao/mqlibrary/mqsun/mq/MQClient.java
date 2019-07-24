package com.tao.mqlibrary.mqsun.mq;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQClient extends MqttClient {
    public MQClient(String serverURI, String clientId) throws MqttException {
        super(serverURI, clientId);
    }

    public MQClient(String serverURI, String clientId, MqttClientPersistence persistence) throws MqttException {
        super(serverURI, clientId, persistence);
    }

    public    IMqttDeliveryToken mqublish(String topic, MqttMessage message) throws MqttException {
        IMqttDeliveryToken publish = super.aClient.publish(topic, message);
        return publish;
    }
}
