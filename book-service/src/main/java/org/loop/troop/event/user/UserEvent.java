package org.loop.troop.event.user;

import lombok.Data;

@Data
public class UserEvent {
    private String userId;
    private String eventType;
    private String timestamp;
}
