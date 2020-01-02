package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.VideoStaffEx;

import java.util.List;

public interface VideoStaffExDao {

    List<VideoStaffEx> queryVideoStaffsByAid(int aid);

}
