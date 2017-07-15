package com.restthougts.templatedemo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.restthougts.templatedemo.model.User;

public class UserUtil {
	
	private static AtomicLong counter = new AtomicLong();

	public static List<User> populateMockUsers() {
		List<User> users = new ArrayList<>();
		users.add(new User(counter.incrementAndGet(), "Peter", 10, 50000));
		users.add(new User(counter.incrementAndGet(), "Sam", 40, 50000));
		users.add(new User(counter.incrementAndGet(), "Calvin", 45, 30000));
		users.add(new User(counter.incrementAndGet(), "Harry", 69, 25000));
		
		return users;
	}

}
