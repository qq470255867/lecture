package com.icycraft.league_lecture.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data

public class SubmitedUser {

    @Excel(name = "ε§ε" ,orderNum = "1")
    private String name;


    @Excel(name = "ηΆζ" ,orderNum = "2")
    private String status;
}
