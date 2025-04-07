package com.bunnyxt.tdd.service.impl.user;

import com.bunnyxt.tdd.dao.user.UserFavoriteVideoDao;
import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserFavoriteVideoEx;
import com.bunnyxt.tdd.model.user.UserFavoriteVideo;
import com.bunnyxt.tdd.service.user.UserFavoriteVideoService;
import com.bunnyxt.tdd.util.CalendarUtil;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFavoriteVideoServiceImpl implements UserFavoriteVideoService {

    @Autowired
    UserFavoriteVideoDao userFavoriteVideoDao;

    @Override
    public TddCommonResponse postUserFavoriteVideo(Long userid, Long aid) {
        Integer added = CalendarUtil.getNowTs();

        TddCommonResponse response;
        try {
            userFavoriteVideoDao.addUserFavoriteVideo(added, userid, aid);
            response = new TddCommonResponse("success", "add favorite video aid " + aid);
        } catch (DuplicateKeyException e) {
            response = new TddCommonResponse("fail", "already added favorite video aid " + aid);
        } catch (DataIntegrityViolationException e) {
            response = new TddCommonResponse("fail", "not found video aid " + aid);
        }

        return response;
    }

    @Override
    public TddCommonResponse deleteUserFavoriteVideo(Long userid, Long aid) {
        TddCommonResponse response;

        Integer delCol = userFavoriteVideoDao.deleteUserFavoriteVideo(userid, aid);
        if (delCol == 1) {
            response = new TddCommonResponse("success", "delete favorite video aid " + aid);
        } else if (delCol == 0) {
            response = new TddCommonResponse("fail", "have not added favorite video aid " + aid);
        } else {
            response = new TddCommonResponse("fail", "del col " + delCol);
        }

        return response;
    }

    @Override
    public UserFavoriteVideo queryUserFavoriteVideo(Long userid, Long aid) {
        return userFavoriteVideoDao.queryUserFavoriteVideo(userid, aid);
    }

    @Override
    public Integer queryUserFavoriteVideoCount(Long aid) {
        return userFavoriteVideoDao.queryUserFavoriteVideoCount(aid);
    }

    @Override
    public List<UserFavoriteVideo> queryUserFavoriteVideoUsers(Long aid, Integer start_ts, Integer end_ts, String order_by, Integer desc, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 100);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return userFavoriteVideoDao.queryUserFavoriteVideoUsers(aid, start_ts, end_ts, order_by, desc, offset, ps);
    }

    @Override
    public Integer queryUserFavoriteVideoUsersCount(Long aid, Integer start_ts, Integer end_ts) {
        return userFavoriteVideoDao.queryUserFavoriteVideoUsersCount(aid, start_ts, end_ts);
    }
}
