package com.icycraft.league_lecture.service;

import com.icycraft.league_lecture.entity.Clazz;
import com.icycraft.league_lecture.entity.ClazzFaq;
import com.icycraft.league_lecture.entity.CreateReq;

import java.util.List;

public interface ClazzService {


    Clazz getClazz(long id);

    Clazz updateClazz(Clazz clazz);

    Clazz validClazzCode(long clazzId,String code);

    List<Clazz> getClazzs();

    Clazz getClazzByUserId(Long userId);

    List<ClazzFaq> getClazzFaqs();

    CreateReq addCreateInfo(CreateReq create);

    List<CreateReq> getCreateReqsUnDo();

    void dealCreateReq(int reqId,int status);
}
