package com.bunnyxt.tdd.service.impl.user;

import com.bunnyxt.tdd.dao.user.UserFavoriteMemberExDao;
import com.bunnyxt.tdd.model.user.UserFavoriteMemberEx;
import com.bunnyxt.tdd.service.user.UserFavoriteMemberExService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFavoriteMemberExServiceImpl implements UserFavoriteMemberExService {

    @Autowired
    UserFavoriteMemberExDao userFavoriteMemberExDao;

    @Override
    public List<UserFavoriteMemberEx> queryUserFavoriteMemberExsMe(Long userid, String name, String sex, String order_by, Integer desc, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 20);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        // modify order_by
        if (order_by.equals("added")) {
            order_by = "u.added"; // here order by added of user favorite time
        }

        return userFavoriteMemberExDao.queryUserFavoriteMemberExsMe(userid, name, sex, order_by, desc, offset, ps);
    }

    @Override
    public Integer queryUserFavoriteMemberExsMeCount(Long userid, String name, String sex) {
        return userFavoriteMemberExDao.queryUserFavoriteMemberExsMeCount(userid, name, sex);
    }

    @Override
    public List<UserFavoriteMemberEx> queryUserFavoriteMemberExs(Long userid, Integer start_ts, Integer end_ts, String order_by, Integer desc, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 100);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        // modify order_by
        if (order_by.equals("added")) {
            order_by = "u.added"; // here order by added of user favorite time
        }

        return userFavoriteMemberExDao.queryUserFavoriteMemberExs(userid, start_ts, end_ts, order_by, desc, offset, ps);
    }

    @Override
    public Integer queryUserFavoriteMemberExsCount(Long userid, Integer start_ts, Integer end_ts) {
        return userFavoriteMemberExDao.queryUserFavoriteMemberExsCount(userid, start_ts, end_ts);
    }
}
