package com.icycraft.league_lecture.controller;

import com.icycraft.league_lecture.entity.WebResult;
import com.icycraft.league_lecture.service.FaqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/faq")
@Slf4j
public class FAQController {


    @Autowired
    FaqService faqService;

    @GetMapping("/get")
    public WebResult getClazz(){
        try {
            return WebResult.SUCCESS(faqService.getFaq());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }
}
