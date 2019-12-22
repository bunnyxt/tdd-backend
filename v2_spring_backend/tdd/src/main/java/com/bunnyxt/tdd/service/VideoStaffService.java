package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.VideoStaff;

import java.util.List;

public interface VideoStaffService {

    List<VideoStaff> queryVideoStaffsByAid(int aid);

}
