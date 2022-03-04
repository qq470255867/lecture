package com.icycraft.league_lecture.controller;

import com.icycraft.league_lecture.entity.WebResult;
import com.icycraft.league_lecture.service.VersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
@Slf4j
public class VersionController {

    @Autowired
    VersionService versionService;


    @RequestMapping("/get/list")
    public WebResult getList(){
        try {
           return WebResult.SUCCESS(versionService.getVersionList());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }
}
