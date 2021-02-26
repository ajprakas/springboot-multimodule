package com.ajay.example.mongo;

import brave.sampler.Sampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserDBController {
    @Autowired
    private UserRepo userRepo;

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    @GetMapping
    public List<User> getAllUsers() {
        LOG.info("Finding all users from mongo-service");
        return userRepo.findAll();
    }

    @GetMapping(value = "{id}")
    public Optional<User> getUser(@PathVariable String id) {
        return userRepo.findById(id);
    }

    @PostMapping
    public ResponseEntity addUser(@RequestBody User user){
        User user1;
        try {
             user1 = userRepo.save(user);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(user1);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity updateUser(@PathVariable String id, @RequestBody User userReq){
        Optional<User> entity = userRepo.findById(id);
        if(entity.isPresent()){
            entity.get().setName(userReq.getName());
            userRepo.save(entity.get());
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with given id not present");
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteUser(@PathVariable String id){
        try {
            userRepo.deleteById(id);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception throws");
        }

        return ResponseEntity.ok("Deleted user");
    }



}
