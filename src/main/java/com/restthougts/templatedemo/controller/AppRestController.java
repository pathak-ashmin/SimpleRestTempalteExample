package com.restthougts.templatedemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.restthougts.templatedemo.model.User;
import com.restthougts.templatedemo.service.UserService;

@RestController
public class AppRestController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
		User user = userService.findById(id);

		if (user == null) {
			System.out.println("User with id: " + id + "not found.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder uCBuilder) {
		if (userService.userExists(user)) {
			System.out.println("A user with name " + user.getName() + "already exists.");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		userService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uCBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {

		User currentUser = userService.findById(id);

		if (currentUser == null) {
			System.out.println("User with id " + id + "not found.");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		currentUser.setName(user.getName());
		currentUser.setAge(user.getAge());
		currentUser.setSalary(user.getSalary());

		userService.updateUser(currentUser);

		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
		 User user = userService.findById(id);
		  
		 if(user == null){
			 System.out.println("User with id " + id + "doesnt exist.");
			 new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
		 
		 userService.deleteUserById(id);
		 
		 return new ResponseEntity<User>(HttpStatus.NO_CONTENT);

	}
	
	@RequestMapping(value="/user/" , method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers(){
		userService.deleteAllUsers();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
}
