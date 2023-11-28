package com.Abhay.Loveseat.Service;

import com.Abhay.Loveseat.Model.UserEntity;
import com.Abhay.Loveseat.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    @Autowired
   private UserRepository userRepository;
    public Page<UserEntity> userList(Pageable pageable){
        return userRepository.findAll(pageable);
    }
    @Transactional
    public void updateStatus(Long id,boolean status){
        userRepository.updateStatus(id,status);
    }

    public Page<UserEntity> searchByName(String name,Pageable page){
        return userRepository.searchByName(name,page);
    }

}
