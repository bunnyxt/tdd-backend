package com.bunnyxt.tdd.service.impl.user;

import com.bunnyxt.tdd.dao.user.UserLikeVideoDao;
import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserLikeVideo;
import com.bunnyxt.tdd.service.user.UserLikeVideoService;
import com.bunnyxt.tdd.util.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class UserLikeVideoServiceImpl implements UserLikeVideoService {

    @Autowired
    UserLikeVideoDao userLikeVideoDao;

    @Override
    public TddCommonResponse postUserLikeVideo(Long userid, Integer aid) {
        Integer added = CalendarUtil.getNowTs();

        TddCommonResponse response;
        try {
            userLikeVideoDao.addUserLikeVideo(added, userid, aid);
            response = new TddCommonResponse("success", "add like video aid " + aid);
        } catch (DuplicateKeyException e) {
            response = new TddCommonResponse("fail", "already added like video aid " + aid);
        } catch (DataIntegrityViolationException e) {
            response = new TddCommonResponse("fail", "not found video aid " + aid);
        }

        return response;
    }

    @Override
    public TddCommonResponse deleteUserLikeVideo(Long userid, Integer aid) {
        TddCommonResponse response;

        Integer delCol = userLikeVideoDao.deleteUserLikeVideo(userid, aid);
        if (delCol == 1) {
            response = new TddCommonResponse("success", "delete like video aid " + aid);
        } else if (delCol == 0) {
            response = new TddCommonResponse("fail", "have not added like video aid " + aid);
        } else {
            response = new TddCommonResponse("fail", "del col " + delCol);
        }

        return response;
    }

    @Override
    public UserLikeVideo queryUserLikeVideo(Long userid, Integer aid) {
        return userLikeVideoDao.queryUserLikeVideo(userid, aid);
    }

    @Override
    public Integer queryUserLikeVideoCount(Integer aid) {
        return userLikeVideoDao.queryUserLikeVideoCount(aid);
    }
}
