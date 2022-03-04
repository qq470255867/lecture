package com.icycraft.league_lecture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icycraft.league_lecture.dao.ClazzDao;
import com.icycraft.league_lecture.dao.UserDao;
import com.icycraft.league_lecture.entity.Clazz;
import com.icycraft.league_lecture.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService{

    @Autowired
    ClazzDao clazzDao;


    @Autowired
    UserDao userDao;

    @Override
    public Clazz getClazz(long id) {

        Clazz clazz = clazzDao.selectById(id);
        QueryWrapper<User> uqw = new QueryWrapper<>();
        uqw.eq("clazz_id", clazz.getId());

        Integer uqwCount = userDao.selectCount(uqw);

        clazz.setRealPnum(uqwCount);

        return clazz;
    }

    @Override
    public Clazz updateClazz(Clazz clazz) {
        clazzDao.updateById(clazz);
        return clazz;
    }


    @Override
    public Clazz validClazzCode(long clazzId,String code) {

        Clazz clazz = clazzDao.selectById(clazzId);

        if (code.equals(clazz.getCode())){
            return clazz;
        }else {
            return null;
        }


    }

    @Override
    public List<Clazz> getClazzs() {
        return clazzDao.selectList(new QueryWrapper<>());
    }

    @Override
    public Clazz getClazzByUserId(Long userId) {
        return clazzDao.selectOne(new QueryWrapper<Clazz>().eq("user_id",userId));
    }
}
