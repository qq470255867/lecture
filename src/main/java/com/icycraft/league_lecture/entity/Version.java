package com.icycraft.league_lecture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Version {


    @TableId(type = IdType.AUTO)
    private long id;

    private String name;
}
