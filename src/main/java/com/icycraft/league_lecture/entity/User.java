package com.icycraft.league_lecture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class User {


    @TableId(type = IdType.AUTO)
    private long id;

    private String name;

    private String avatar;

    private long clazzId;

    private int roleId;

    private String mail;

    private String wxId;

    private String ip;

    private String address;

    private String location;

    private int catchEgg;

}
