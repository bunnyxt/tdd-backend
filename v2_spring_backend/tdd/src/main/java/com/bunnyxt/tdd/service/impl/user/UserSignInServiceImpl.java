package com.bunnyxt.tdd.service.impl.user;
import com.bunnyxt.tdd.dao.user.*;
import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserSignIn;
import com.bunnyxt.tdd.model.user.UserSignInOverview;
import com.bunnyxt.tdd.service.user.UserSignInService;
import com.bunnyxt.tdd.util.CalendarUtil;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserSignInServiceImpl implements UserSignInService {

    @Autowired
    UserSignInDao userSignInDao;

    @Autowired
    UserSignInOverviewDao userSignInOverviewDao;

    @Autowired
    UserDao userDao;

    @Autowired
    UserHistoryPointDao userHistoryPointDao;

    @Autowired
    UserHistoryExpDao userHistoryExpDao;

    @Override
    public List<UserSignIn> queryUserSignIns(Long userid, Integer last_count, Integer start_ts, Integer end_ts, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 100);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return userSignInDao.queryUserSignIns(userid, last_count, start_ts, end_ts, offset, ps);
    }

    @Override
    public Integer queryUserSignInsCount(Long userid, Integer start_ts, Integer end_ts) {
        return userSignInDao.queryUserSignInsCount(userid, start_ts, end_ts);
    }

    @Override
    public TddCommonResponse postUserSignIn(Long userid) {
        // check today added or not
        Integer start_ts = CalendarUtil.getTodayStartTs(); // today start ts
        Integer end_ts = start_ts + 24 * 60 * 60 - 1; // today end ts
        UserSignIn todayUserSignIn = userSignInDao.queryUserSignInOneDay(userid, start_ts, end_ts);
        if (todayUserSignIn != null) {
            return new TddCommonResponse("fail", "user have already signed in today");
        }

        // go sign in
        Integer added = CalendarUtil.getNowTs();
        userSignInDao.addUserSignIn(added, userid);

        // calc today sign in rank
        Integer rank = userSignInDao.queryUserSignInRankInOneDay(userid, start_ts, end_ts);

        // set rank
        userSignInDao.updateUserSignInRank(added, userid, rank);

        // get overview and check, then update overview
        UserSignInOverview userSignInOverview = userSignInOverviewDao.queryUserSignInOverviewByUserid(userid);
        Integer last_added = userSignInOverview.getLast_added();
        Integer new_last_added_days = 1;
        if (last_added == null) {
            // first sign in
            userSignInOverviewDao.updateUserSignInOverviewByUseridWhenSignIn(userid, added, 1);
        } else {
            if (last_added >= start_ts - 24 * 60 * 60 && last_added <= end_ts - 24 * 60 * 60) {
                // yesterday signed in, last_added_days++
                new_last_added_days = userSignInOverview.getLast_added_days() + 1;
            }
            userSignInOverviewDao.updateUserSignInOverviewByUseridWhenSignIn(userid, added, new_last_added_days);
        }

        // add point
        double[] addPointArr = {0, 5, 10, 15, 20, 25};
        double addPoint = addPointArr[new_last_added_days > 5 ? 5 : new_last_added_days];
        userDao.updateUserPointById(addPoint, userid);
        userHistoryPointDao.addUserHistoryPoint(added, userid, addPoint, "每日登录奖励");

        // add exp
        double[] addExpArr = {0, 5, 10, 15, 20, 25};
        double addExp = addExpArr[new_last_added_days > 5 ? 5 : new_last_added_days];
        userDao.updateUserExpById(addExp, userid);
        userHistoryExpDao.addUserHistoryExp(added, userid, addExp, "每日登录奖励");

        // return
        Map<String, Object> detail = new LinkedHashMap<>();
        detail.put("total", userSignInOverview.getTotal() + 1); // total signed in days
        detail.put("consecutive_days", new_last_added_days); // consecutive signed in days
        detail.put("point", addPoint); // earned point
        detail.put("exp", addExp); // earned exp
        detail.put("rank", rank); // today signed in rank
        return new TddCommonResponse("success", "sign in success", detail);
    }
}
