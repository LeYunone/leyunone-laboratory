package com.leyunone.laboratory.core.concurrent.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

// 定义消息类
class Greet {
    final String message;

    Greet(String message) {
        this.message = message;
    }
}

// 定义 Actor 类
class Greeter extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Greet.class, greet -> {
                    System.out.println("Received message: " + greet.message);
                })
                .build();
    }
}

public class AkkaExample {
    public static void main(String[] args) {
        // 创建 Actor 系统
        ActorSystem system = ActorSystem.create("MyActorSystem");

        // 创建 Greeter Actor
        ActorRef greeter = system.actorOf(Props.create(Greeter.class), "greeter");

        // 发送消息给 Greeter Actor
        greeter.tell(new Greet("Hello, Akka!"), ActorRef.noSender());

        // 关闭 Actor 系统
        system.terminate();
    }
}