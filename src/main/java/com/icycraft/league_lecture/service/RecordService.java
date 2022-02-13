package com.icycraft.league_lecture.service;

import com.icycraft.league_lecture.entity.Clazz;
import com.icycraft.league_lecture.entity.Record;
import com.icycraft.league_lecture.entity.User;

import java.util.List;

public interface RecordService {


    Record addRecord(Record record);

    Record getLastRecordByUserId(long userId);


    List<User> getSubmitedList(long clazzId);

}
