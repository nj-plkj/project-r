package com.ladyishenlong.rprojectzuul.security;

import com.ladyishenlong.rprojectzuul.model.UserModel;
import com.ladyishenlong.rprojectzuul.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @Author ruanchenhao
 * @Date 2019-05-16 11:44
 * <p>
 * 控制用户登录 连接数据库作比较
 */
@Component
public class SecurityUserDetails implements UserDetailsService {


    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel userModel =userService.findUserByUsername(username);
        if(userModel==null)throw new UsernameNotFoundException(username);
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(userModel.getRoles()));
        return new User(userModel.getUsername(),
                encoder.encode(userModel.getPassword()),
                authorities);
    }


}
