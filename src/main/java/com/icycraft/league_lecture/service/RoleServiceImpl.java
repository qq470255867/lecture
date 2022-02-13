package com.icycraft.league_lecture.service;

import com.icycraft.league_lecture.dao.RoleDao;
import com.icycraft.league_lecture.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleDao roleDao;



    @Override
    public Role getRole(long id) {
        return roleDao.selectById(id);
    }
}
