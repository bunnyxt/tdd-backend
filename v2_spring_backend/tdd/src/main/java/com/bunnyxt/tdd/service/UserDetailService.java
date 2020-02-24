package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

}
