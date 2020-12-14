package com.example.demo.security.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.entity.request.Login;
import com.example.demo.exeption.notfound.UserNotFoundException;
import com.example.demo.mapping.UserMappingDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.dto.SingUpDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SecurityService implements UserDetailsService {

    private SingUpDto userDao;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto singUp(SingUpDto singUpDto) {
        singUpDto.setPassword(bCryptPasswordEncoder.encode(singUpDto.getPassword()));

        User user = new User();
        user.setEmail(singUpDto.getEmail());
        user.setFirstName(singUpDto.getFirstName());
        user.setLastName(singUpDto.getLastName());
        user.setPassword(singUpDto.getPassword());
        user.setId(System.currentTimeMillis());
        user.setFollowers(new ArrayList<>());
        user.setFollowing(new ArrayList<>());
        user = userService.saveUser(user);

        return UserMappingDto.getInitialUserDto(user);
    }


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with " + email + " email not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    public boolean isPasswordValid(Login loginModel) {
        String userEncodedPassword = userRepository.getUserIdByEmail(loginModel.getEmail()).getPassword();
        return bCryptPasswordEncoder.matches(loginModel.getPassword(), userEncodedPassword);
    }
}
