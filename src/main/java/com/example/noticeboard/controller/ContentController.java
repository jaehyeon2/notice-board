package com.example.noticeboard.controller;

import com.example.noticeboard.domain.Category;
import com.example.noticeboard.domain.Content;
import com.example.noticeboard.domain.User;
import com.example.noticeboard.domain.VisibleStatus;
import com.example.noticeboard.service.CategoryService;
import com.example.noticeboard.service.ContentService;
import com.example.noticeboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping("/content/write")
    public String createForm(Model model){
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("contentForm", new ContentForm());

        return "content/createContentForm";
    }

    @PostMapping("/content/write")
    public String create(@Valid ContentForm contentForm, BindingResult result){
        if(result.hasErrors()){
            return "content/createContentForm";
        }

        Content content = new Content();

        if(contentForm.getStatus().equals("visible")){
            content.setStatus(VisibleStatus.VISIBLE);
        }else{
            content.setStatus(VisibleStatus.HIDDEN);
        }

        content.setTitle(contentForm.getTitle());
        content.setText(contentForm.getText());
        content.setUser(userService.findUser(contentForm.getUserId()));
        content.setCategory(categoryService.findCategory(contentForm.getCategoryId()));
        content.setUploadDate(LocalDateTime.now());

        contentService.register(content);

        return "redirect/";
    }

    //유저의 글 확인
    @GetMapping("/content/contentList/{un}")
    public String ucontents(@PathVariable("un") Long un, Model model){
        List<Content> contents = contentService.findListU(un);
        String name = contentService.findNameU(un) + "의 글보기";
        model.addAttribute("name", name);
        model.addAttribute("contents", contents);
        return "content/contentList";
    }

    //카테고리별 글 확인
    @GetMapping("/content/contentList/{cn}")
    public String ccontents(@PathVariable("cn") Long cn, Model model){
        List<Content> contents = contentService.findListC(cn);
        String name = contentService.findNameU(cn) + " 글보기";
        model.addAttribute("name", name);
        model.addAttribute("contents", contents);
        return "content/contentList";
    }

    //콘텐츠 편집 페이지 이동
    @GetMapping("/content/edit/{contentId}")
    public String getContent(@PathVariable("contentId") Long contentId, Model model){
        Content content = contentService.findById(contentId);
        model.addAttribute("content", content);
        return "content/edit";
    }

    //콘텐츠 편집
    @PostMapping("/content/edit/{contentId}")
    public String edit(@RequestParam("status") String status,
                       @RequestParam("text") String text,
                       @RequestParam("password") String password,
                       @PathVariable("contentId") Long contentId){
        if (contentService.checkPassword(contentId, password)){
            if(status.equals("visible")){
                contentService.update(contentId, text, VisibleStatus.VISIBLE);
            }else{
                contentService.update(contentId, text, VisibleStatus.HIDDEN);
            }
            return "redirect/";
        }else{
            return "redirect/content.edit/{contentId}";
        }
    }
}
