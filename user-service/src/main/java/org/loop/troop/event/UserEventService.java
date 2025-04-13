package org.loop.troop.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.loop.troop.event.user.UserCreatedEvent;
import org.loop.troop.event.user.UserDeletedEvent;
import org.loop.troop.event.user.UserEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service

public class UserEventService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @Value("${spring.kafka.producer.topic}")
    private String topic;

    public UserEventService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void createUser(String userId) {
        UserCreatedEvent event = new UserCreatedEvent(userId);
        sendEvent(event);
    }

    public void deleteUser(String userId) {
        UserDeletedEvent event = new UserDeletedEvent(userId);
        sendEvent(event);
    }

    private void sendEvent(UserEvent userEvent) {
        try {
            String json = objectMapper.writeValueAsString(userEvent);
            kafkaTemplate.send(topic, json);
            log.info("Event sent: {} for userId: {}", userEvent.getEventType(), userEvent.getUserId());
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize UserEvent: {}", userEvent, e);
        }
    }
}

