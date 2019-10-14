package com.demo.uploads.demo.service;

import com.demo.uploads.demo.entity.dto.UserDto;
import com.demo.uploads.demo.entity.error.EmailExistsException;
import com.demo.uploads.demo.repository.UserRepository;
import com.demo.uploads.demo.entity.repository.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        User user = repo.findByEmail(username);
        if (user == null) {
            return null;
        }
        List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
        String password = user.getPassword();
        return new org.springframework.security.core.userdetails.User(username, password, auth);
    }

    public void registerNewUserAccount(UserDto userDto) {
        if (repo.existsByEmail(userDto.getEmail())) {
            throw new EmailExistsException(userDto.getEmail());
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        repo.save(user);
    }

}