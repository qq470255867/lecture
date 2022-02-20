package com.icycraft.league_lecture.controller;

import com.icycraft.league_lecture.entity.User;
import com.icycraft.league_lecture.entity.WebResult;
import com.icycraft.league_lecture.service.UserService;
import com.icycraft.league_lecture.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {


    @Autowired
    UserService userService;

    @RequestMapping("/get/openId/{code}")
    public WebResult getUserOpenId(@PathVariable("code")String code){
        try {
            String openId =userService.getOpenId(code);
            return WebResult.SUCCESS(openId);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

    @RequestMapping("/login/{openId}")
    public WebResult login(@PathVariable("openId")String openId, HttpServletRequest httpServletRequest){
        try {

            String ip = IpUtil.getIpAddr(httpServletRequest);
            User user = userService.getUerByOpenId(openId,ip);
            return WebResult.SUCCESS(user);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

}
