package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.VideoAidTitle;

import java.util.List;

public interface VideoAidTitleService {

    List<VideoAidTitle> queryVideoAidTitle(Long aid);

}
