package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.VideoBvidTitle;

import java.util.List;

public interface VideoBvidTitleService {

    List<VideoBvidTitle> queryVideoBvidTitle(String bvid);
}
