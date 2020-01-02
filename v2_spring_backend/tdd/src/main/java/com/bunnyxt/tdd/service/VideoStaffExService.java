package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.VideoStaffEx;

import java.util.List;

public interface VideoStaffExService {

    List<VideoStaffEx> queryVideoStaffsByAid(int aid);
}
