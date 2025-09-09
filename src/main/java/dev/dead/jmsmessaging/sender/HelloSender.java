package dev.dead.jmsmessaging.sender;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dead.jmsmessaging.config.JmsConfig;
import dev.dead.jmsmessaging.model.HelloWorldMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
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
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private AtomicInteger counter = new AtomicInteger(0);

    @Scheduled(fixedRate = 8000, initialDelay = 30000)
    public void sendMessage() {
        log.debug("Send Message to Queue");
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder()
                .uuid(UUID.randomUUID())
                .message("Hello World!: [" + counter.incrementAndGet() + "]")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, helloWorldMessage);
        log.debug("Sent Message to Queue");


    }

    @Scheduled(fixedRate = 8000, initialDelay = 3000)
    public void sendAndReceiveMessage() throws JMSException {
        log.debug("Send Message to Queue");
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder()
                .uuid(UUID.randomUUID())
                .message("Hello World!: [" + counter.incrementAndGet() + "]")
                .build();

        Message rec = jmsTemplate.sendAndReceive(JmsConfig.MY_callback_QUEUE, session -> {
            Message helloMessage = null;
            try {
                helloMessage = session.createTextMessage(objectMapper.writeValueAsString(helloWorldMessage));
                helloMessage.setIntProperty("counter", counter.incrementAndGet());
                helloMessage.setStringProperty("_type", "dev.dead.jmsmessaging.model.HelloWorldMessage");
                return helloMessage;
            } catch (JsonProcessingException e) {
                throw new JMSException("error converting message to json"
                        , e.getMessage()

                );
            }

        });
        log.debug("rec:" + rec.getBody(String.class));


    }
}
