package com.icycraft.league_lecture.controller;

import com.icycraft.league_lecture.entity.Lecture;
import com.icycraft.league_lecture.entity.WebResult;
import com.icycraft.league_lecture.service.LectureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lec")
@Slf4j
public class LectureController {


    @Autowired
    LectureService lectureService;

    @GetMapping("/getLectures")
    public WebResult getLectures(){

        try {
            return WebResult.SUCCESS(lectureService.getLectures());
        }catch (Exception e){

            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }
    @GetMapping("/get/curLecture")
    public WebResult getCurLecture(){

        try {
            return WebResult.SUCCESS(lectureService.getLastLecture());
        }catch (Exception e){

            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

    @PostMapping("/addLectures")
    public WebResult addLectures(@RequestBody Lecture lecture){

        try {
            return WebResult.SUCCESS(lectureService.addLectures(lecture));
        }catch (Exception e){

            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }
}