package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.VideoStaff;

import java.util.List;

public interface VideoStaffDao {

    List<VideoStaff> queryVideoStaffsByAid(Long aid);
}
