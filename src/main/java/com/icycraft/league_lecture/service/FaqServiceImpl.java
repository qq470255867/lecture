package com.icycraft.league_lecture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icycraft.league_lecture.dao.FaqDAO;
import com.icycraft.league_lecture.entity.Faq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FaqServiceImpl implements FaqService{

    @Autowired
    FaqDAO faqDAO;




    @Override
    public List<Faq> getFaq() {
        return faqDAO.selectList(new QueryWrapper<>());
    }
}
