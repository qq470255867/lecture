package com.icycraft.league_lecture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;


@Data
public class Lecture {

    @TableId(type = IdType.AUTO)
    private long id;

    private String name;

    private String date;


}
