package com.icycraft.league_lecture.service;

import com.icycraft.league_lecture.entity.Clazz;

import java.util.List;

public interface ClazzService {


    Clazz getClazz(long id);

    Clazz updateClazz(Clazz clazz);

    Clazz validClazzCode(long clazzId,String code);

    List<Clazz> getClazzs();

    Clazz getClazzByUserId(Long userId);
}
