package com.icycraft.league_lecture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icycraft.league_lecture.dao.LectureDao;
import com.icycraft.league_lecture.dao.RecordDao;
import com.icycraft.league_lecture.entity.Record;
import com.icycraft.league_lecture.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService{


    @Autowired
    RecordDao recordDao;

    @Autowired
    LectureDao lectureDao;

    @Override
    public Record addRecord(Record record) {

        QueryWrapper<Record> eq = new QueryWrapper<Record>().eq("user_id", record.getUserId()).eq("lec_id", record.getLecId());
        Record oldRecord = recordDao.selectOne(eq);


        if (oldRecord==null){
            recordDao.insert(record);
        }else {
            record.setId(oldRecord.getId());
            recordDao.updateById(record);
        }



        return record;
    }

    @Override
    public Record getLastRecordByUserId(long userId) {

        return recordDao.getLastRecordByUserId(userId);

    }



    @Override
    public List<User> getSubmitedList(long clazzId) {

        return recordDao.getSubmited(clazzId);
    }
}
