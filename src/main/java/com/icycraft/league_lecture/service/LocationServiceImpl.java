package com.icycraft.league_lecture.service;

import com.alibaba.fastjson.JSONObject;
import com.icycraft.league_lecture.dao.UserDao;
import com.icycraft.league_lecture.entity.Location;
import com.icycraft.league_lecture.entity.User;

import com.icycraft.league_lecture.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationServiceImpl implements LocationService{


    @Autowired
    RestTemplate restTemplate;



    @Override
    public String convertAddress(String lon, String lat) {

        String url = "http://api.cellocation.com:82/regeo/?lat="+lat+"&lon="+lon;

        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);

        JSONObject body = JSONObject.parseObject(entity.getBody());

        return body.getString("address");
    }

    @Override
    public Location analyseIp(User newUser) {
        return null;
    }


}
