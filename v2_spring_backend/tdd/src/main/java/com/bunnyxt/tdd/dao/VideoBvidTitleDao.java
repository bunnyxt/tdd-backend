package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.VideoBvidTitle;

import java.util.List;

public interface VideoBvidTitleDao {

    List<VideoBvidTitle> queryVideoBvidTitle(String bvid);
}
