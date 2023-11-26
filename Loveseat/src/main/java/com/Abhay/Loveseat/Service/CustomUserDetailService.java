package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    public UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user=userRepository.findByEmail(username);
        if (user==null){
            throw new UsernameNotFoundException("user note found");
        }
        return new CustomUserDetail(user);
    }
}
