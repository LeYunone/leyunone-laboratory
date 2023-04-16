//package com.leyunone.laboratory.core.mq;
//
//import com.leyunone.laboratory.core.util.SslUtil;
//import io.netty.handler.codec.mqtt.MqttProperties;
//import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//import java.security.KeyManagementException;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.CertificateException;
//import java.time.Clock;
//
///**
// * @author LeYunone
// * @email 365627310@qq.com
// * @date 2023-04-02
// */
//@Configuration
//public class MqttConfig {
//
//    private static final Logger logger = LoggerFactory.getLogger(MqttConfig.class);
//
//    @Bean
//    public MqttConnectOptions mqttConnectOptions() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
//        MqttConnectOptions options = new MqttConnectOptions();
//        options.setUserName("mqtt用户名");
//        options.setServerURIs(new String[]{"http://81.68.137.33/"});
//        options.setPassword("mqtt密码".toCharArray());
//        options.setCleanSession(true);
//        options.setKeepAliveInterval(90);
//        options.setAutomaticReconnect(true);
//        options.setMaxInflight(10000);
//        options.setConnectionTimeout(120);
//        options.setSocketFactory(SslUtil.getSslSocktet("ssl证书地址"));
//        return options;
//    }
//
//    @Bean
//    public MqttAsyncClient mqttAsyncClient(MqttConnectOptions mqttConnectOptions) {
//        MqttAsyncClient sampleClient = null;
//        try {
//            sampleClient = new MqttAsyncClient("http://81.68.137.33/", "clientId");
//            sampleClient.connect(mqttConnectOptions);
//            boolean successful = sampleClient.isConnected();
//            long startTime = Clock.systemDefaultZone().millis();
//            long timeout = Integer.parseInt("超时时间") * 1000;
//            long endTime = startTime;
//            while (!successful && (endTime - startTime) <= timeout) {
//                Thread.sleep(10);
//                successful = sampleClient.isConnected();
//                endTime = Clock.systemDefaultZone().millis();
//            }
//            if (!successful) {
//                Thread.currentThread().interrupt();
//                throw new RuntimeException("mqtt client connect is timeout");
//            }
//        } catch (Exception e) {
//            logger.error("mqtt client connect is failed.");
//        }
//        return sampleClient;
//    }
//}
