package com.itechart.courses.authentication;

import com.itechart.courses.dao.user.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

    @Autowired(required = true)
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        com.itechart.courses.entity.User user = userDAO.readUser(login);
        if(user != null) {
            List<GrantedAuthority> authorities = buildUserAuthority(user);
            logger.info("User with login " + user.getLogin() + " has logged in");
            return buildUserForAuthentication(user, authorities);
        }else {
            throw new UsernameNotFoundException("Can't locate user '" + login + "'");
        }
    }

    private List<GrantedAuthority> buildUserAuthority(com.itechart.courses.entity.User user) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        setAuths.add(new SimpleGrantedAuthority(user.getRole().name()));

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }

    private User buildUserForAuthentication(com.itechart.courses.entity.User user,
                                            List<GrantedAuthority> authorities) {
        System.out.println(user.getLogin());
        return new User(user.getLogin(), user.getPassword(),
                !user.getIsDelete(), true, true, true, authorities);
    }
}