package com.icycraft.league_lecture.controller;

import com.icycraft.league_lecture.entity.WebResult;
import com.icycraft.league_lecture.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {


    @Autowired
    RoleService roleService;


    @GetMapping("/get/{id}")
    public WebResult getRole(@PathVariable("id") long id){
        try {
            return  WebResult.SUCCESS(roleService.getRole(id));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }
}
