package com.example.noticeboard.repository;

import com.example.noticeboard.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public List<User> findByNickName(String nickname){
        return em.createQuery("select u from User u where u.nickname = :nickname", User.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public User findById(Long id){
        return em.find(User.class, id);
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
}
