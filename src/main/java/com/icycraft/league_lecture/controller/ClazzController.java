package com.icycraft.league_lecture.controller;

import com.icycraft.league_lecture.entity.Clazz;
import com.icycraft.league_lecture.entity.WebResult;
import com.icycraft.league_lecture.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/clazz")
@Slf4j
public class ClazzController {

    @Autowired
    ClazzService clazzService;

    @PostMapping("/update")
    public WebResult updateClazz(@RequestBody Clazz clazz){
        try {
            return WebResult.SUCCESS(clazzService.updateClazz(clazz));
        }catch (Exception e){

            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

    @GetMapping ("/get/{id}")
    public WebResult getClazz(@PathVariable long id){
        try {
            Clazz clazz = clazzService.getClazz(id);
            return WebResult.SUCCESS(clazz);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

    @GetMapping ("/valid/{code}/{clazzId}")
    public WebResult validCode(@PathVariable("code") String code,@PathVariable("clazzId") long clazzId){
        try {
            return WebResult.SUCCESS(clazzService.validClazzCode(clazzId,code));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

    @GetMapping("/get/list")
    public WebResult getClazzs(){
        try {
            return WebResult.SUCCESS(clazzService.getClazzs());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }
}
