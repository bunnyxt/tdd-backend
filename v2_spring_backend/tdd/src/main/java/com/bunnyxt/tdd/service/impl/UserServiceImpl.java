package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.RoleDao;
import com.bunnyxt.tdd.dao.UserDao;
import com.bunnyxt.tdd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO check validity of username
        User user = userDao.queryUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("cannot find user with username " + username);
        }

        user.setRoles(roleDao.queryRolesByUserId(user.getId()));

        return user;
    }
}
