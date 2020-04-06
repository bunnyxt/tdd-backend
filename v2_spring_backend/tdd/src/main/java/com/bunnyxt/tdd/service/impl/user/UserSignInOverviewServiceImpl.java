package com.bunnyxt.tdd.service.impl.user;

import com.bunnyxt.tdd.dao.user.UserSignInOverviewDao;
import com.bunnyxt.tdd.model.user.UserSignInOverview;
import com.bunnyxt.tdd.service.user.UserSignInOverviewService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSignInOverviewServiceImpl implements UserSignInOverviewService {

    @Autowired
    UserSignInOverviewDao userSignInOverviewDao;

    @Override
    public UserSignInOverview queryUserSignInOverviewByUserid(Long userid) {
        return userSignInOverviewDao.queryUserSignInOverviewByUserid(userid);
    }

    @Override
    public List<UserSignInOverview> queryUserSignInOverviews(Integer start_ts, Integer end_ts, String order_by, Integer desc, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 100);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return userSignInOverviewDao.queryUserSignInOverviews(start_ts, end_ts, order_by, desc, offset, ps);
    }

    @Override
    public Integer queryUserSignInOverviewsCount(Integer start_ts, Integer end_ts) {
        return userSignInOverviewDao.queryUserSignInOverviewsCount(start_ts, end_ts);
    }
}
