package com.icycraft.league_lecture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Clazz {

    @TableId()
    private long id;

    private String name;

    private int pnum;

    @TableField(exist = false)
    private int realPnum;

    private String code;


}
