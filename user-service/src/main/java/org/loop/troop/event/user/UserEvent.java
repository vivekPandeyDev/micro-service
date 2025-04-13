package org.loop.troop.event.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;

@Data
public class UserEvent {
    private String userId;
    private String eventType;
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    public UserEvent(String userId,String eventType){
        this.userId = userId;
        this.eventType = eventType;
    }

    public UserEvent() {
    }
}
