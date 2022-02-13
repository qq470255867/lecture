package com.icycraft.league_lecture.service;

import com.alibaba.fastjson.JSONObject;
import com.icycraft.league_lecture.entity.User;

import java.util.List;

public interface UserService {


    User getUerByOpenId(String openId);

    String getOpenId(String code) throws Exception;

    User updateUser(User user);

    User getUser(long id);

    List<JSONObject> getUserWithStatus(long clazzId);
}
