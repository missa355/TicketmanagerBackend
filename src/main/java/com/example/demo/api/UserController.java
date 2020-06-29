package com.example.demo.api;


import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.rmi.server.UID;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/User")

public class UserController {

    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void addUser(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.addUser(user);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{id}")
    public User getUserById(@PathVariable("id") String uid){
        return userService.getUserById(uid)
                .orElse(null);
    }
    @DeleteMapping(path = "/{id}")
    public void deleteUserById(@PathVariable("id") String uid){
        userService.deleteUserById(uid);
    }

    @GetMapping(path = "login/{id}/{password}")
    public boolean getIfMatches(@PathVariable("id") String uid,@PathVariable("password") String Password){
//        System.out.println(uid + Password);
        User curr_user = userService.getUserById(uid)
                            .orElse(null);
        return bCryptPasswordEncoder.matches(Password, curr_user.getPassword());
    }


}
