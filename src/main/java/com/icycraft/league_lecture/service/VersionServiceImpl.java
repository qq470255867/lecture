package com.icycraft.league_lecture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icycraft.league_lecture.dao.VersionDao;
import com.icycraft.league_lecture.entity.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VersionServiceImpl implements VersionService{

    @Autowired
    VersionDao versionDao;


    @Override
    public List<Version> getVersionList() {
        return versionDao.selectList(new QueryWrapper<>());
    }
}
