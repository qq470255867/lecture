package com.icycraft.league_lecture.controller;

import com.icycraft.league_lecture.entity.Clazz;
import com.icycraft.league_lecture.entity.CreateReq;
import com.icycraft.league_lecture.entity.WebResult;
import com.icycraft.league_lecture.service.ClazzService;
import com.icycraft.league_lecture.service.MailService;
import com.icycraft.league_lecture.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.OutputBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/clazz")
@Slf4j
public class ClazzController {

    @Autowired
    ClazzService clazzService;

    @Autowired
    MailService mailService;

    @Autowired
    UserService userService;

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

    @GetMapping("/faq/list")
    public WebResult getFaqList(){
        try {
            return WebResult.SUCCESS(clazzService.getClazzFaqs());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

    @PostMapping("/create")
    public WebResult crateClazz(@RequestBody CreateReq create){
        try {
            clazzService.addCreateInfo(create);

            mailService.sendMail("470255867@qq.com","来自团课截图提交小程序的消息",create.getName()+"申请创建班级"+create.getClazz());
            return WebResult.SUCCESS(create);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

    @GetMapping("/get/list/create")
    public WebResult getCreateReqs(){
        try {
            return WebResult.SUCCESS(clazzService.getCreateReqsUnDo());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

    @GetMapping("/deal/create/req/{reqId}/{status}")
    public WebResult dealCreateReq(@PathVariable("reqId") int reqId,@PathVariable int status){
        //status    //0 未处理 1 通过 2 拒绝
        try {
            clazzService.dealCreateReq(reqId,status);
            return WebResult.SUCCESS(null);
        }catch (Exception e ){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

}
