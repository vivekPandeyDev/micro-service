package org.loop.troop.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.loop.troop.event.user.UserCreatedEvent;
import org.loop.troop.event.user.UserDeletedEvent;
import org.loop.troop.event.user.UserEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserEventService {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private String topic;

    public void createUser(String userId) {
        UserCreatedEvent event = new UserCreatedEvent(userId);
        sendEvent(event);
    }

    public void deleteUser(String userId) {
        UserDeletedEvent event = new UserDeletedEvent(userId);
        sendEvent(event);
    }

    private void sendEvent(UserEvent event) {
        kafkaTemplate.send(topic, event);
        log.info("Event sent: {} for userId: {}",event.getEventType(),event.getUserId());
    }  
}
