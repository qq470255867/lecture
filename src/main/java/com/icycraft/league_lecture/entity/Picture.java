package com.icycraft.league_lecture.entity;

import lombok.Data;

@Data
public class Picture {

    private String id;

    private String userId;

    private String beforeStudySrc1;

    private String beforeStudySrc2;

    private String infoSrc;

    private String finishSrc;

    private String forwardSrc;

    private int PicNum;
}
