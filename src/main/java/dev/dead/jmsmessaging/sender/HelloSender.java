package dev.dead.jmsmessaging.sender;


import dev.dead.jmsmessaging.config.JmsConfig;
import dev.dead.jmsmessaging.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
@RequiredArgsConstructor
public class HelloSender {
    private AtomicInteger counter = new AtomicInteger(0);
    private  final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 8000, initialDelay = 3000)
    public void sendMessage(){
        log.debug("Send Message to Queue");
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder()
                .uuid(UUID.randomUUID())
                .message("Hello World!: [" + counter.incrementAndGet() + "]")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE,helloWorldMessage);
        log.debug("Sent Message to Queue");


    }
}
