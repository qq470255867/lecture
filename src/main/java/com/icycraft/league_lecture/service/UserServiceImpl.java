package com.icycraft.league_lecture.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icycraft.league_lecture.dao.*;
import com.icycraft.league_lecture.entity.User;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{



    @Autowired
    ClazzDao clazzDao;

    @Autowired
    LectureDao lectureDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    UserDao userDao;

    @Autowired
    RestTemplate restTemplate;

    @Value("${app.id}")
    private String appId;

    @Value("${app.secret}")
    private String appSecret;


    @Autowired
    RecordDao recordDao;


    @Override
    public User getUerByOpenId(String openId) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("wx_id",openId);
        User user = userDao.selectOne(userQueryWrapper);

        if (user==null){
            //首次登录
            User newUser = new User();
            newUser.setWxId(openId);
            userDao.insert(newUser);
            return userDao.selectOne(userQueryWrapper);
        }else {
            return user;
        }
    }



    @Override
    public String getOpenId(String code) throws Exception{

        String url ="https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code";
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);

        JSONObject body = JSONObject.parseObject(entity.getBody());
        assert body != null;
        if (StringUtils.isEmpty(body.getString("openid"))){
            throw new Exception("调用登录接口失败");
        }else {
            return body.getString("openid");
        }
    }

    @Override
    public User updateUser(User user) {

        userDao.updateById(user);
        return user;
    }

    @Override
    public User getUser(long id) {
        return userDao.selectById(id);
    }

    @Override
    public List<JSONObject> getUserWithStatus(long clazzId) {


        List<User> usersInOneClazz = userDao.selectList(new QueryWrapper<User>().eq("clazz_id", clazzId));

        List<User> submitedUsers = recordDao.getSubmited(clazzId);

        ArrayList<JSONObject> result = new ArrayList<>();
        for (User inOneClazz : usersInOneClazz) {
            JSONObject jo = new JSONObject();
            jo.put("user",inOneClazz);
            jo.put("status",false);
            for (User submitedUser : submitedUsers) {
                if (submitedUser.getId()==inOneClazz.getId()){
                    jo.put("status",true);
                }
            }
            result.add(jo);
        }
        return result;
    }


}
