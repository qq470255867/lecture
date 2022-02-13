package com.icycraft.league_lecture.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icycraft.league_lecture.entity.Clazz;
import com.icycraft.league_lecture.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<User> {
}
