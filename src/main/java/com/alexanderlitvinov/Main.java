package com.alexanderlitvinov;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Main {

    private static final String BASE_URL = "http://94.198.50.185:7081/api/users";
    private static RestTemplate restTemplate = new RestTemplate();
    private static String cookies;


    public static void main(String[] args) {
        getAllUsers();
        createUser(new User(3L, "James", "Brown", (byte) 28));
        updateUser(new User(3L, "Thomas", "Shelby", (byte) 30));
        deleteUser(3L);
        System.out.println();
    }

    private static void getAllUsers() {
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL, String.class);
        cookies = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        System.out.println("Users: " + response.getBody());
    }

    private static void createUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.COOKIE, cookies);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, request, String.class);
        System.out.print(response.getBody());
    }

    private static void updateUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.COOKIE, cookies);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, request, String.class);
        System.out.print(response.getBody());
    }

    private static void deleteUser(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, cookies);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/" + userId, HttpMethod.DELETE, request, String.class);
        System.out.print(response.getBody());
    }
}