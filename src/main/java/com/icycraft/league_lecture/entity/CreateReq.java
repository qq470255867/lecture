package com.icycraft.league_lecture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CreateReq {


    @TableId(type = IdType.AUTO)
    private long id;

    private String school;

    private String clazz;

    private String name;

    private String mail;

    private String info;

    private String wxId;

    //0 未处理 1 通过 2 拒绝
    private int status;

}
