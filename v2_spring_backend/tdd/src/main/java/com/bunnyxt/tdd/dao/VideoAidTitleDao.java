package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.VideoAidTitle;

import java.util.List;

public interface VideoAidTitleDao {

    List<VideoAidTitle> queryVideoAidTitle(Long aid);

}
