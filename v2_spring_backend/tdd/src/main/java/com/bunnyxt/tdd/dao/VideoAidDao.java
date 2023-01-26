package com.bunnyxt.tdd.dao;

import java.util.List;

public interface VideoAidDao {

    Long queryVideoAidMaxId();

    Integer queryVideoAidById(Long id);

    List<Integer> queryVideoAidsByIds(List<Long> ids);

}
