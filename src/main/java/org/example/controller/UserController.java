package org.example.controller;

import org.example.entity.Base;
import org.example.entity.User;
import org.example.service.BaseService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {
    private UserService userService;
    private BaseService baseService;

    @Autowired
    public UserController(UserService userService, BaseService baseService) {
        this.userService = userService;
        this.baseService = baseService;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        System.out.println("Получаем всех пользователей");
        List<User> users = userService.getAllUser();
        model.addAttribute("employees", users);
        return "main";
    }

    @PostMapping("/main")
    public String addProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam int rating,
            @RequestParam("date") String dateTime,
            Model model
    ) {
        System.out.println("Могешь когда хочешь");
        Base base = new Base();
        base.setName(name);
        base.setDescription(description);
        base.setRating(rating);
//        base.setTime(dateTime);
        baseService.createOrUpdateBase(base);
        model.addAttribute("base", base);

        return "main";
    }

    @GetMapping("/main/{id}")
    public String getById(@PathVariable int id, Model model) {
        Base base = baseService.getBaseById(id);
        model.addAttribute("task", base);
        return "info";
    }

    @GetMapping("/main/all")
    public String getById(Model model) {
//        List<Base> base = baseService.getAllBase();
        model.addAttribute("all", baseService.getAllBase());

        return "all";
    }

}
