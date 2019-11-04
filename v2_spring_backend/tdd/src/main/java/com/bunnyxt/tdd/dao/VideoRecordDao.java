package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.VideoRecord;

import java.util.List;

public interface VideoRecordDao {

    List<VideoRecord> queryVideoRecordsByAid(int aid);

}
