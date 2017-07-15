package com.restthougts.templatedemo.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restthougts.templatedemo.model.User;
import com.restthougts.templatedemo.util.UserUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static List<User> users;

	static {
		users = UserUtil.populateMockUsers();
	}

	@Override
	public User findById(long id) {

		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	@Override
	public User findByName(String name) {
		for (User user : users) {
			if (user.getName().equalsIgnoreCase(name)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public void saveUser(User user) {

		if (users != null && !users.isEmpty()) {
			user.setId(users.get(users.size() - 1).getId() + 1);
		}
		users.add(user);

	}

	@Override
	public void updateUser(User user) {
		int index = users.indexOf(user);
		users.set(index, user);
	}

	@Override
	public void deleteUserById(long id) {
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			if (user.getId() == id) {
				iterator.remove();
			}
		}
	}

	@Override
	public List<User> findAllUsers() {
		return users;
	}

	@Override
	public void deleteAllUsers() {
		users.clear();
	}

	@Override
	public boolean userExists(User user) {
		return findByName(user.getName()) != null;
	}

}
