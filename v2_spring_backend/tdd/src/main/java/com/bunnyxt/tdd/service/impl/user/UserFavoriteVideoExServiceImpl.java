package com.bunnyxt.tdd.service.impl.user;

import com.bunnyxt.tdd.dao.user.UserFavoriteVideoExDao;
import com.bunnyxt.tdd.model.user.UserFavoriteVideoEx;
import com.bunnyxt.tdd.service.user.UserFavoriteVideoExService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFavoriteVideoExServiceImpl implements UserFavoriteVideoExService {

    @Autowired
    UserFavoriteVideoExDao userFavoriteVideoExDao;

    @Override
    public List<UserFavoriteVideoEx> queryUserFavoriteVideoExsMe(Long userid, String title, String order_by, Integer desc, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 20);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        // modify order_by
        if (order_by.equals("added")) {
            order_by = "u.added"; // here order by added of user favorite time
        }
        if (order_by.equals("like")) {
            order_by = "r.like"; // cannot be like since like is a possible keyword there
        }

        return userFavoriteVideoExDao.queryUserFavoriteVideoExsMe(userid, title, order_by, desc, offset, ps);
    }

    @Override
    public Integer queryUserFavoriteVideoExsMeCount(Long userid, String title) {
        return userFavoriteVideoExDao.queryUserFavoriteVideoExsMeCount(userid, title);
    }
}
