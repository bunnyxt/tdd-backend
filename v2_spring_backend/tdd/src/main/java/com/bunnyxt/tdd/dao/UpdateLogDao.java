package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.UpdateLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UpdateLogDao {

    List<UpdateLog> queryUpdateLogs(@Param("last_count") Integer last_count);

}
