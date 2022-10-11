package com.example.noticeboard.controller;

import com.example.noticeboard.domain.User;
import com.example.noticeboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/signup")
    public String createForm(Model model){
        model.addAttribute("userForm", new UserForm());
        return "user/createUserForm";
    }

    @PostMapping("/user/signup")
    public String create(@Valid UserForm userForm, BindingResult result){
        if(result.hasErrors()){
            return "user/createUserForm";
        }
        User user = new User();
        user.setNickname(userForm.getNickname());
        user.setPassword(userForm.getPassword());

        userService.join(user);
        return "redirect/";
    }

    @GetMapping("/user/userList")
    public String users(Model model){
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "user/userList";
    }
}
