package org.loop.troop.event.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserEventListener {

    private final ObjectMapper objectMapper;
    @KafkaListener(topics = "${spring.kafka.consumer.user-listen-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) throws JsonProcessingException {
        UserEvent userEvent = objectMapper.readValue(message, UserEvent.class);
        log.info("user-event type: {}",userEvent.getEventType());
    }
}
