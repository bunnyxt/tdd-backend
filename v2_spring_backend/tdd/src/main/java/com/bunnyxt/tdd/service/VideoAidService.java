package com.bunnyxt.tdd.service;

import java.util.List;

public interface VideoAidService {

    Integer queryVideoAidMaxId();

    Integer queryVideoAidById(Integer id);

    List<Integer> queryVideoAidsByIds(List<Integer> ids);

}
