package com.example.noticeboard.component;

import com.example.noticeboard.domain.Category;
import com.example.noticeboard.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class CategoryInit {

    private final CategoryService categoryService;

    @PostConstruct
    public void CM(){
        Category c1 = new Category();
        c1.setName("board1");
        Category c2 = new Category();
        c2.setName("board2");
        Category c3 = new Category();
        c3.setName("board3");
    }
}
