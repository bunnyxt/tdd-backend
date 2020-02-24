package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.RoleDao;
import com.bunnyxt.tdd.dao.UserDao;
import com.bunnyxt.tdd.model.User;
import com.bunnyxt.tdd.service.UserService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Override
    public User queryUserById(Long id) {
        return userDao.queryUserById(id);
    }

    @Override
    public List<User> queryUsers(String username, String email, String phone, Integer enabled, String role,
                                 String order_by, Integer desc, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 20);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return userDao.queryUsers(username, email, phone, enabled, role, order_by, desc, offset, ps);
    }

    @Override
    public Integer queryUsersCount(String username, String email, String phone, Integer enabled, String role) {
        return userDao.queryUsersCount(username, email, phone, enabled, role);
    }
}
