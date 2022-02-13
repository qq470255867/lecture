package com.icycraft.league_lecture.service;

import com.icycraft.league_lecture.entity.Lecture;

import java.util.List;

public interface LectureService {


    List<Lecture> getLectures();


    Lecture addLectures(Lecture lecture);


    Lecture getLastLecture();
}
