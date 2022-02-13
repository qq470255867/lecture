package com.icycraft.league_lecture.controller;

import com.alibaba.fastjson.JSONObject;
import com.icycraft.league_lecture.entity.*;
import com.icycraft.league_lecture.service.ClazzService;
import com.icycraft.league_lecture.service.LectureService;
import com.icycraft.league_lecture.service.RecordService;
import com.icycraft.league_lecture.service.UserService;
import com.icycraft.league_lecture.util.ExcelUtiles;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

@RequestMapping("/record")
@Slf4j
@RestController
public class RecordController {

    @Autowired
    RecordService recordService;

    @Autowired
    LectureService lectureService;

    @Autowired
    UserService userService;

    @Autowired
    ClazzService clazzService;

    @GetMapping("/submit/{userId}")
    public WebResult getSubmitRecord(@PathVariable("userId") long userId){
        try {
          return WebResult.SUCCESS(recordService.getLastRecordByUserId(userId));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

   @GetMapping("/submited/list/{clazzId}")
   public WebResult getSubmitedlist(@PathVariable("clazzId") long clazzId){
       try {
           return WebResult.SUCCESS(recordService.getSubmitedList(clazzId));
       }catch (Exception e){
           log.error(e.getMessage(),e);
           return WebResult.ERROR(e.getMessage());
       }
   }






   @RequestMapping("/add/{userId}")
   public WebResult addRecord(@PathVariable("userId") long userId){
       try {
           Lecture lastLecture = lectureService.getLastLecture();
           SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           Record record = new Record();

           record.setUserId(userId);
           record.setLecId(lastLecture.getId());
           record.setDate(simpleDateFormat.format(new Date()));
           return WebResult.SUCCESS(recordService.addRecord(record));
       }catch (Exception e){
           log.error(e.getMessage(),e);
           return WebResult.ERROR(e.getMessage());
       }
   }
    @GetMapping("/report/excel/{clazzId}/{type}")
    public void  reportExcel(@PathVariable("clazzId") long clazzId, @PathVariable("type") int type, HttpServletResponse response){
        try {
            Clazz clazz = clazzService.getClazz(clazzId);
            Lecture lastLecture = lectureService.getLastLecture();
            List<JSONObject> userWithStatus = userService.getUserWithStatus(clazzId);
            ArrayList<SubmitedUser> submitedUsers = new ArrayList<>();

            String excelName = "";
            if (type==0){
                excelName = clazz.getName()+"的全部提交记录";
                userWithStatus.forEach(jsonObject -> {
                    SubmitedUser submitedUser = new SubmitedUser();
                    User user = jsonObject.getObject("user", User.class);
                    submitedUser.setName(user.getName());
                    if (jsonObject.getBoolean("status")){
                        submitedUser.setStatus("已提交");
                    }else {
                        submitedUser.setStatus("未提交");
                    }
                    submitedUsers.add(submitedUser);
                });
            }else if (type==1){
                excelName = clazz.getName()+"的已提交记录";
                userWithStatus.forEach(jsonObject -> {
                    if (jsonObject.getBoolean("status")){
                        SubmitedUser submitedUser = new SubmitedUser();
                        User user = jsonObject.getObject("user", User.class);
                        submitedUser.setName(user.getName());
                        submitedUser.setStatus("已提交");
                        submitedUsers.add(submitedUser);
                    }
                });
            }else if (type==2) {
                excelName = clazz.getName()+"的未提交记录";
                userWithStatus.forEach(jsonObject -> {
                    if (!jsonObject.getBoolean("status")){
                        SubmitedUser submitedUser = new SubmitedUser();
                        User user = jsonObject.getObject("user", User.class);
                        submitedUser.setName(user.getName());
                        submitedUser.setStatus("未提交");
                        submitedUsers.add(submitedUser);
                    }
                });
            }

            ExcelUtiles.exportExcel(submitedUsers,excelName,lastLecture.getName(),SubmitedUser.class,excelName+".xlsx",response);


        }catch (Exception e){
            log.error(e.getMessage(),e);

        }
   }



}
