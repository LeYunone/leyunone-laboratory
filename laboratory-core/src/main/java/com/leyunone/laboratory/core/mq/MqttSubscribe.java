//package com.leyunone.laboratory.core.mq;
//
//import com.alibaba.fastjson.JSONObject;
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.springframework.stereotype.Service;
//
//import java.nio.charset.StandardCharsets;
//
///**
// * @author LeYunone
// * @email 365627310@qq.com
// * @date 2023-04-02
// */
//@Service
//public class MqttSubscribe implements MqttCallback {
//    @Override
//    public void connectionLost(Throwable throwable) {
//        //失去连接 -  重连
//    }
//
//    @Override
//    public void messageArrived(String s, MqttMessage mqttMessage) {
//        String topic = s;
//        //消息
//        JSONObject messageJson = JSONObject.parseObject(new String(mqttMessage.getPayload(), StandardCharsets.UTF_8));
//    }
//
//    @Override
//    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//        //断开连接
//    }
//}
