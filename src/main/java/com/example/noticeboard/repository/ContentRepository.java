package com.example.noticeboard.repository;

import com.example.noticeboard.domain.Content;
import com.example.noticeboard.domain.VisibleStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContentRepository {
    private final EntityManager em;
    public void save(Content content){
        em.persist(content);
    }

    public Content findById(Long id){
        return em.find(Content.class, id);
    }

    public List<Content> findByCID(Long categoryId){
        return em.createQuery("select c from Content c where c.category.id = :categoryId" +
                " and c.status = :visible", Content.class)
                .setParameter("categoryId", categoryId)
                .setParameter("visible", VisibleStatus.VISIBLE)
                .getResultList();
    }

    public List<Content> findByUID(Long userId){
        return em.createQuery("select c from Content c where c.user.id = :userId", Content.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
