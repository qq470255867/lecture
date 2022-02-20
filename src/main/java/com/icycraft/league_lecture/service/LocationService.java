package com.icycraft.league_lecture.service;

import com.icycraft.league_lecture.entity.Location;
import com.icycraft.league_lecture.entity.User;

public interface LocationService {


    String convertAddress(String lon,String lat);


    Location analyseIp(User newUser);
}
