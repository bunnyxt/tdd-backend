package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.AidRange;
import com.bunnyxt.tdd.model.VideoAidTitle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoAidTitleDao {

    List<VideoAidTitle> queryVideoAidTitle(@Param("ranges") List<AidRange> ranges);

}
