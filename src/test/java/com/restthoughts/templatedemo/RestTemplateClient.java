package com.restthoughts.templatedemo;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.restthougts.templatedemo.model.User;

public class RestTemplateClient {

	public static final String REST_SERVICE_URI = "http://localhost:8080/restTemplateDemo";

	@SuppressWarnings("unchecked")
	private static void getAllUsers() {
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI + "/user/",
				List.class);

		if (usersMap != null) {
			for (LinkedHashMap<String, Object> map : usersMap) {
				System.out.println("User : id = " + map.get("id") + ", Name=" + map.get("name") + ", Age="
						+ map.get("age") + ", Salary=" + map.get("salary"));
			}
		} else {
			System.out.println("No user exists");
		}

	}
	
	private static void getUserById(){
		RestTemplate restTemplate = new RestTemplate();
		User user = restTemplate.getForObject(REST_SERVICE_URI + "/user/1", User.class);
		System.out.println();
		System.out.println(user);
	}
	
	private static void createUser(){
		RestTemplate restTemplate = new RestTemplate();
		User user = new User(0, "David", 35, 72000);
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/user/", user, User.class);
		System.out.println("Location :" + uri.toASCIIString());
	}
	
	private static void updateUser(){
		RestTemplate restTemplate = new RestTemplate();
		User user = new User(1, "Hank", 45, 82000);
		restTemplate.put(REST_SERVICE_URI + "/user/1", user);
		System.out.println(user);
		
	}
	
	public static void deleteUser(){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI + "/user/2");
	}
	
	public static void deleteAllUsers(){
		RestTemplate restTempalte = new RestTemplate();
		restTempalte.delete(REST_SERVICE_URI + "/user/");
	}
	

	public static void main(String[] args) {
		getAllUsers();
		getUserById();
		createUser();
		getAllUsers();
		updateUser();
		getAllUsers();
		deleteUser();
		getAllUsers();
		deleteAllUsers();
		getAllUsers();
	}
}
