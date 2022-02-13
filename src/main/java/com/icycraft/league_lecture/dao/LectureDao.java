package com.icycraft.league_lecture.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icycraft.league_lecture.entity.Clazz;
import com.icycraft.league_lecture.entity.Lecture;
import com.icycraft.league_lecture.entity.Record;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureDao extends BaseMapper<Lecture> {


    @Select("SELECT * FROM lecture ORDER BY date desc limit 1")
    Lecture getLastLecture();

}
