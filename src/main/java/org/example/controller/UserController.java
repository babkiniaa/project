package org.example.controller;

import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping()
    public String getAllUsers(Model model){
        System.out.println("Получаем всех пользователей");
        List<User> users = userService.getAllUser();
        model.addAttribute("employees", users);
        return "employes.html";
    }


}
