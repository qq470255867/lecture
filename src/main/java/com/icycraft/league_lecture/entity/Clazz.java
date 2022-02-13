package com.icycraft.league_lecture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Clazz {

    @TableId(type = IdType.AUTO)
    private long id;

    private String name;

    private int pnum;

    private String code;


}
