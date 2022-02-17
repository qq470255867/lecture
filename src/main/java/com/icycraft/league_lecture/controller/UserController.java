package com.icycraft.league_lecture.controller;

import com.icycraft.league_lecture.entity.User;
import com.icycraft.league_lecture.entity.WebResult;
import com.icycraft.league_lecture.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    @Autowired
    UserService userService;

    @PostMapping("/update")
    public WebResult updateUser(@RequestBody User user) {

        try {
            return WebResult.SUCCESS(userService.updateUser(user));
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }

    }


    @GetMapping("/get/{id}")
    public WebResult getUser(@PathVariable("id") long id) {

        try {
            return WebResult.SUCCESS(userService.getUser(id));
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }

    }

    @GetMapping("/get/clazz/with/status/{clazzId}")
    public WebResult getUserWithStatus(@PathVariable("clazzId") long clazzId) {

        try {

            return WebResult.SUCCESS(userService.getUserWithStatus(clazzId));
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }

    }

}
