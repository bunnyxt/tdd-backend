package com.bunnyxt.tdd.dao;

import java.util.List;

public interface VideoAidDao {

    Integer queryVideoAidMaxId();

    Integer queryVideoAidById(Integer id);

    List<Integer> queryVideoAidsByIds(List<Integer> ids);

}
