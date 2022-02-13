package com.icycraft.league_lecture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;



@Data
public class Record {


    @TableId(type = IdType.AUTO)
    private long id;

    private long userId;

    private long lecId;

    private String date;


}
