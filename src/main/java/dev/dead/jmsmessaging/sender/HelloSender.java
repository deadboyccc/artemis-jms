package dev.dead.jmsmessaging.sender;


import dev.dead.jmsmessaging.config.JmsConfig;
import dev.dead.jmsmessaging.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class HelloSender {
    private  final JmsTemplate jmsTemplate;
    @Scheduled(fixedRate = 3000,initialDelay = 2500)
    public void sendMessage(){
        log.debug("Send Message to Queue");
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder()
                .uuid(UUID.randomUUID())
                .message("Hello World!")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE,helloWorldMessage);
        log.debug("Sent Message to Queue");


    }
}
