package com.ajay.example.userApp;

import brave.sampler.Sampler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URISyntaxException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private MongoServiceClient mongoServiceClient;

    @Autowired
    private ObjectMapper mapper;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    @GetMapping
    public ResponseEntity<List<Object>> getAllUsers(){
        LOG.info("hitting user-service to find all users");
        return ResponseEntity.ok(mongoServiceClient.getAllUsers());
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id){
        return ResponseEntity.ok(mongoServiceClient.getUser(id));
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) throws JsonProcessingException {
         String res = mongoServiceClient.createUser(user);
//         User user1 = mapper.readValue(res, User.class);
         return ResponseEntity.ok(res);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) throws URISyntaxException {
        User user1 = mongoServiceClient.updateUser(user, id);
        if(user!=null){
            return ResponseEntity.ok(user1);
        }
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        mongoServiceClient.deleteUser(id);
        return ResponseEntity.ok("deleted successfully");
    }
}
