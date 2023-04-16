//package com.leyunone.laboratory.core.mq;
//
//import cn.hutool.core.util.ObjectUtil;
//import com.alibaba.excel.util.StringUtils;
//import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * @author LeYunone
// * @email 365627310@qq.com
// * @date 2023-04-02
// */
//@Service
//public class MqttPublish {
//    
//    @Autowired
//    private MqttAsyncClient mqttAsyncClient;
//
//    public boolean messagePublish(String topic,String message,Integer qos) {
////        String topic = "topic-mqtt主题";
//        if (StringUtils.isBlank(topic) || StringUtils.isBlank(message)) {
//            return false;
//        }
//        MqttMessage mqttMessage = new MqttMessage();
//        mqttMessage.setPayload(message.getBytes());
//        mqttMessage.setQos(ObjectUtil.isNull(qos) ? 0 : qos);
//        try {
//            mqttAsyncClient.publish(topic, mqttMessage);
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }
//}
