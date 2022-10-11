package com.example.noticeboard.service;

import com.example.noticeboard.domain.User;
import com.example.noticeboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Long join(User user){
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }
    private void validateDuplicateUser(User user){
        List<User> findUsers = userRepository.findByNickName(user.getNickname());
        if(!findUsers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다!");
        }
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User findUser(Long userId){
        return userRepository.findById(userId);
    }
}
