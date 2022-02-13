package com.icycraft.league_lecture.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data

public class SubmitedUser {

    @Excel(name = "姓名" ,orderNum = "1")
    private String name;


    @Excel(name = "状态" ,orderNum = "2")
    private String status;
}
