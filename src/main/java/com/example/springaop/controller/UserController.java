package com.example.springaop.controller;

import com.example.springaop.User.User;
import org.springframework.web.bind.annotation.*;

/**
 * @Description TODO
 * @Author zhouxinjian
 * @Date 2019-07-10 14:40
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/add")
    public boolean addUser(@RequestBody User user) {
        throw new RuntimeException();
//        return false;
    }

    @GetMapping("/find/{id}")
    public User findById(@PathVariable("id") int id) {

//        User lisi = User.builder().id(1L).name("lisi").build();

//        return new User();
        throw new RuntimeException();


    }

    @PutMapping("/update")
    public boolean update(@RequestBody User user) {
        return true;
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return true;
    }
}
