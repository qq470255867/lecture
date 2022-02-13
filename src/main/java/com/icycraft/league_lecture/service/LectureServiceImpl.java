package com.icycraft.league_lecture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icycraft.league_lecture.dao.LectureDao;
import com.icycraft.league_lecture.entity.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;




@Service
public class LectureServiceImpl implements LectureService {

    @Autowired

    LectureDao lectureDao;


    @Override
    public List<Lecture> getLectures() {
        return lectureDao.selectList(new QueryWrapper<Lecture>().orderByDesc("date"));
    }

    @Override
    public Lecture addLectures(Lecture lecture) {
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         lecture.setDate(sdf.format(new Date()));
         lectureDao.insert(lecture);
         return lecture;
    }

    @Override
    public Lecture getLastLecture() {
        return lectureDao.getLastLecture();
    }


}
