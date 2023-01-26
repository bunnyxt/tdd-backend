package com.bunnyxt.tdd.service.impl.user;

import com.bunnyxt.tdd.dao.user.UserFavoriteMemberDao;
import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserFavoriteMember;
import com.bunnyxt.tdd.service.user.UserFavoriteMemberService;
import com.bunnyxt.tdd.util.CalendarUtil;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFavoriteMemberServiceImpl implements UserFavoriteMemberService {

    @Autowired
    UserFavoriteMemberDao userFavoriteMemberDao;

    @Override
    public TddCommonResponse postUserFavoriteMember(Long userid, Long mid) {
        Integer added = CalendarUtil.getNowTs();

        TddCommonResponse response;
        try {
            userFavoriteMemberDao.addUserFavoriteMember(added, userid, mid);
            response = new TddCommonResponse("success", "add favorite member mid " + mid);
        } catch (DuplicateKeyException e) {
            response = new TddCommonResponse("fail", "already added favorite member mid " + mid);
        } catch (DataIntegrityViolationException e) {
            response = new TddCommonResponse("fail", "not found member mid " + mid);
        }

        return response;
    }

    @Override
    public TddCommonResponse deleteUserFavoriteMember(Long userid, Long mid) {
        TddCommonResponse response;

        Integer delCol = userFavoriteMemberDao.deleteUserFavoriteMember(userid, mid);
        if (delCol == 1) {
            response = new TddCommonResponse("success", "delete favorite member mid " + mid);
        } else if (delCol == 0) {
            response = new TddCommonResponse("fail", "have not added favorite member mid " + mid);
        } else {
            response = new TddCommonResponse("fail", "del col " + delCol);
        }

        return response;
    }

    @Override
    public UserFavoriteMember queryUserFavoriteMember(Long userid, Long mid) {
        return userFavoriteMemberDao.queryUserFavoriteMember(userid, mid);
    }

    @Override
    public Integer queryUserFavoriteMemberCount(Long mid) {
        return userFavoriteMemberDao.queryUserFavoriteMemberCount(mid);
    }

    @Override
    public List<UserFavoriteMember> queryUserFavoriteMemberUsers(Long mid, Integer start_ts, Integer end_ts, String order_by, Integer desc, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 100);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return userFavoriteMemberDao.queryUserFavoriteMemberUsers(mid, start_ts, end_ts, order_by, desc, offset, ps);
    }

    @Override
    public Integer queryUserFavoriteMemberUsersCount(Long mid, Integer start_ts, Integer end_ts) {
        return userFavoriteMemberDao.queryUserFavoriteMemberUsersCount(mid, start_ts, end_ts);
    }
}
