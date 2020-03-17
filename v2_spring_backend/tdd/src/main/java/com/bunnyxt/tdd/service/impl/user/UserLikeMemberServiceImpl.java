package com.bunnyxt.tdd.service.impl.user;

import com.bunnyxt.tdd.dao.user.UserLikeMemberDao;
import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.model.user.UserLikeMember;
import com.bunnyxt.tdd.service.user.UserLikeMemberService;
import com.bunnyxt.tdd.util.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class UserLikeMemberServiceImpl implements UserLikeMemberService {

    @Autowired
    UserLikeMemberDao userLikeMemberDao;

    @Override
    public TddCommonResponse postUserLikeMember(Long userid, Integer mid) {
        Integer added = CalendarUtil.getNowTs();

        TddCommonResponse response;
        try {
            userLikeMemberDao.addUserLikeMember(added, userid, mid);
            response = new TddCommonResponse("success", "add like member mid " + mid);
        } catch (DuplicateKeyException e) {
            response = new TddCommonResponse("fail", "already added like member mid " + mid);
        } catch (DataIntegrityViolationException e) {
            response = new TddCommonResponse("fail", "not found member mid " + mid);
        }

        return response;
    }

    @Override
    public TddCommonResponse deleteUserLikeMember(Long userid, Integer mid) {
        TddCommonResponse response;

        Integer delCol = userLikeMemberDao.deleteUserLikeMember(userid, mid);
        if (delCol == 1) {
            response = new TddCommonResponse("success", "delete like member mid " + mid);
        } else if (delCol == 0) {
            response = new TddCommonResponse("fail", "have not added like member mid " + mid);
        } else {
            response = new TddCommonResponse("fail", "del col " + delCol);
        }

        return response;
    }

    @Override
    public UserLikeMember queryUserLikeMember(Long userid, Integer mid) {
        return userLikeMemberDao.queryUserLikeMember(userid, mid);
    }

    @Override
    public Integer queryUserLikeMemberCount(Integer mid) {
        return userLikeMemberDao.queryUserLikeMemberCount(mid);
    }
}
