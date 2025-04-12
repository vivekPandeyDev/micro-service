package org.loop.troop.event.user;

public class UserCreatedEvent extends UserEvent {
    public UserCreatedEvent(String userId){
        super(userId, "USER_CREATED");
    }
}
