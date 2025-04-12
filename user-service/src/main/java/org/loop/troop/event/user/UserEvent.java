package org.loop.troop.event.user;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserEvent {
    private String userId;
    private String eventType;
    private LocalDateTime timestamp = LocalDateTime.now();

    public UserEvent(String userId,String eventType){
        this.userId = userId;
        this.eventType = eventType;
    }
}
