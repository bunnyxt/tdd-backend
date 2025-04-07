package com.bunnyxt.tdd.service;

import java.util.List;

public interface VideoAidService {

    Long queryVideoAidMaxId();

    Long queryVideoAidById(Long id);

    List<Long> queryVideoAidsByIds(List<Long> ids);

}
