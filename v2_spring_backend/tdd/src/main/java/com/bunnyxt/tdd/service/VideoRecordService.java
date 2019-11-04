package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.VideoRecord;

import java.util.List;

public interface VideoRecordService {

    List<VideoRecord> queryVideoRecordsByAid(int aid);

}
