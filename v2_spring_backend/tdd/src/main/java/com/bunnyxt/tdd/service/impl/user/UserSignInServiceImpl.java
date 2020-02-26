package com.bunnyxt.tdd.service.impl.user;
import com.bunnyxt.tdd.dao.user.UserSignInDao;
import com.bunnyxt.tdd.model.user.UserSignIn;
import com.bunnyxt.tdd.service.user.UserSignInService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSignInServiceImpl implements UserSignInService {

    @Autowired
    UserSignInDao userSignInDao;

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
}
