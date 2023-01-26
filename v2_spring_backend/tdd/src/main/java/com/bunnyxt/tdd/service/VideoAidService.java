package com.bunnyxt.tdd.service;

import java.util.List;

public interface VideoAidService {

    Long queryVideoAidMaxId();

    Integer queryVideoAidById(Long id);

    List<Integer> queryVideoAidsByIds(List<Long> ids);

}
