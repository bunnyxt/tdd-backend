package com.bunnyxt.tdd.auth;

import com.bunnyxt.tdd.model.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class TddAuthUtil {

    public static User GetCurrentUser() {
        User user = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            user = (User)authentication.getPrincipal();
        } catch (ClassCastException e) {
            // user not logged in
//            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}
