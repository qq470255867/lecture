package com.icycraft.league_lecture.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icycraft.league_lecture.entity.Record;
import com.icycraft.league_lecture.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordDao extends BaseMapper<Record> {





    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\trecord \n" +
            "WHERE\n" +
            "\tlec_id = (\n" +
            "\tSELECT\n" +
            "\t\tid \n" +
            "\tFROM\n" +
            "\t\tlecture \n" +
            "\tORDER BY\n" +
            "\tdate DESC \n" +
            "\tLIMIT 1)\n" +
            "\tand user_id = #{userId}")
    Record getLastRecordByUserId(@Param("userId") Long userId);

    @Select("SELECT\n" +
            "\ta.* \n" +
            "FROM\n" +
            "\t`user` a\n" +
            "\tLEFT JOIN record b ON a.id = b.user_id \n" +
            "WHERE\n" +
            "\ta.clazz_id = #{clazzId} \n" +
            "\tAND lec_id = (\n" +
            "\tSELECT\n" +
            "\t\tid \n" +
            "\tFROM\n" +
            "\t\tlecture \n" +
            "\tORDER BY\n" +
            "\tdate DESC \n" +
            "\tLIMIT 1)")
    List<User> getSubmited(@Param("clazzId") Long clazzId);
}
