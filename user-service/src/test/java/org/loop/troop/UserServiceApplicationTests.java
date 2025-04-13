package org.loop.troop;

import org.junit.jupiter.api.Test;
import org.loop.troop.event.UserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
	UserEventService userEventService;

	@Test
	void checkUserCreatedEvent() {
		userEventService.createUser("test user id");
	}

}
