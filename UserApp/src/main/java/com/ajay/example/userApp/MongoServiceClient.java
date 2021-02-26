package com.ajay.example.userApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class MongoServiceClient {

    @Value("${db.host}")
    private String dbHost;

    @Value("${db.port}")
    private String dbPort;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    public MongoServiceClient() {
        baseUrl = "http://"+dbHost+":"+dbPort+"/users";
    }

    private String baseUrl;

    public User getUser(String id){
        String baseUrl = "http://"+dbHost+":"+dbPort+"/users";
        String url = baseUrl+"/"+id;
        return restTemplate.getForObject(url, User.class);
    }

    public List<Object> getAllUsers(){
        String baseUrl = "http://"+dbHost+":"+dbPort+"/users";
        return restTemplate.getForObject(baseUrl, List.class);

    }

    public String createUser(User user) throws JsonProcessingException {
        String baseUrl = "http://"+dbHost+":"+dbPort+"/users";
       String response = restTemplate.postForObject(baseUrl, user, String.class);
       return "created "+ response;
    }

    public User updateUser(User user, String id) throws URISyntaxException {
        String baseUrl = "http://"+dbHost+":"+dbPort+"/users"+"/"+id;
        try {
            restTemplate.put(new URI(baseUrl), user);
        }catch(Exception e){
            return null;
        }

        return user;

    }

    public void deleteUser(String id){
        String baseUrl = "http://"+dbHost+":"+dbPort+"/users"+"/"+id;
        restTemplate.delete(baseUrl);
    }



}
