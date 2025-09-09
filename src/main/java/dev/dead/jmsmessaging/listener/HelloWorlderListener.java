package dev.dead.jmsmessaging.listener;

import dev.dead.jmsmessaging.config.JmsConfig;
import dev.dead.jmsmessaging.model.HelloWorldMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class HelloWorlderListener {

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders messageHeaders,
                       Message message) {
        System.out.printf("Hello World Message: %s%n", helloWorldMessage);
        System.out.println("Hello World Message: " + message   );
        System.out.println("Hello World Message: " + messageHeaders);
    }
}
