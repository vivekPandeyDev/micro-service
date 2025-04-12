package org.loop.troop.event.user;

public class UserDeletedEvent extends UserEvent {
    public UserDeletedEvent(String userId){
        super(userId, "USER_DELETED");
    }
}
