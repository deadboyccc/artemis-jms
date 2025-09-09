package dev.dead.jmsmessaging.listener;

import dev.dead.jmsmessaging.config.JmsConfig;
import dev.dead.jmsmessaging.model.HelloWorldMessage;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class HelloWorlderListener {
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders messageHeaders,
                       Message message) {
        System.out.printf("Hello World Message: %s%n", helloWorldMessage);
        System.out.println("Hello World Message: " + message);
        System.out.println("Hello World Message: " + messageHeaders);
    }

    //callback
    @JmsListener(destination = JmsConfig.MY_callback_QUEUE)
    public void listenCallback(@Payload HelloWorldMessage helloWorldMessage,
                               @Headers MessageHeaders messageHeaders,
                               Message message) throws JMSException {
        HelloWorldMessage payloadMessage = HelloWorldMessage.builder()
                .uuid(UUID.randomUUID())
                .message("Bye World")
                .build();
        jmsTemplate.convertAndSend((Destination) message.getJMSReplyTo(), payloadMessage);
    }
}
