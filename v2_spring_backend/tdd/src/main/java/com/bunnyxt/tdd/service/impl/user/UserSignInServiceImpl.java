package com.bunnyxt.tdd.service.impl.user;
import com.bunnyxt.tdd.dao.user.UserDao;
import com.bunnyxt.tdd.dao.user.UserHistoryExpDao;
import com.bunnyxt.tdd.dao.user.UserHistoryPointDao;
import com.bunnyxt.tdd.dao.user.UserSignInDao;
import com.bunnyxt.tdd.model.user.UserSignIn;
import com.bunnyxt.tdd.service.user.UserSignInService;
import com.bunnyxt.tdd.util.CalendarUtil;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserSignInServiceImpl implements UserSignInService {

    @Autowired
    UserSignInDao userSignInDao;

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
    public void postUserSignIn(Long userid) {
        // check today added or not
        Integer start_ts = CalendarUtil.getTodayStartTs();
        Integer end_ts = start_ts + 24 * 60 * 60 - 1;
        UserSignIn todayUserSignIn = userSignInDao.queryUserSignInOneDay(userid, start_ts, end_ts);
        if (todayUserSignIn != null) {
            System.out.println("already added");
            return;
        }

        // go sign in
        Integer added = CalendarUtil.getNowTs();
        userSignInDao.addUserSignIn(added, userid);

        // check last 5 sign in
        List<UserSignIn> last5UserSignIn = userSignInDao.queryUserSignIns(userid, 5, 0, 0, 0, 0);
        int consecutive_sign_in_count = 0;
        int start_ts_t = start_ts;
        int end_ts_t = end_ts;
        for (int i = 0; i < last5UserSignIn.size(); i++) {
            int added_t = last5UserSignIn.get(i).getAdded();
            if (added_t >= start_ts_t && added_t <= end_ts_t) {
                consecutive_sign_in_count++;
                start_ts_t -= 24 * 60 * 60;
                end_ts_t -= 24 * 60 * 60;
            } else {
                break;
            }
        }

        // add point
        double[] addPointArr = {0, 5, 10, 15, 20, 25};
        double addPoint = addPointArr[consecutive_sign_in_count];
        userDao.updateUserPointById(addPoint, userid);
        userHistoryPointDao.addUserHistoryPoint(added, userid, addPoint, "每日登录奖励");

        // add exp
        double[] addExpArr = {0, 5, 10, 15, 20, 25};
        double addExp = addExpArr[consecutive_sign_in_count];
        userDao.updateUserExpById(addExp, userid);
        userHistoryExpDao.addUserHistoryExp(added, userid, addExp, "每日登录奖励");

        // calc today sign in rank
        int rank = userSignInDao.queryUserSignInRankInOneDay(userid, start_ts, end_ts);

        // return
        System.out.println(rank);
    }
}
