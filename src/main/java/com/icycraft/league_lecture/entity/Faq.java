package com.icycraft.league_lecture.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Faq {

    @TableId(type = IdType.AUTO)
    private int id;

    private String content;

}
