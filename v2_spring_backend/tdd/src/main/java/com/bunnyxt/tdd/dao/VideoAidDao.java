package com.bunnyxt.tdd.dao;

import java.util.List;

public interface VideoAidDao {

    Long queryVideoAidMaxId();

    Long queryVideoAidById(Long id);

    List<Long> queryVideoAidsByIds(List<Long> ids);

}
