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
        model.addAttribute("base", new Base());
        return "main";
    }


    @PostMapping("/main")
    public String addProduct(@ModelAttribute("base") Base baseModel,Model model) {
        System.out.println("Могешь когда хочешь");
        baseService.createOrUpdateBase(baseModel);

        return "main";
    }

    @GetMapping("/main/{id}")
    public String getById(@PathVariable int id, Model model) {
        Base base = baseService.getBaseById(id);
        model.addAttribute("task", base);
        return "info";
    }

    @GetMapping("/main/all")
    public String getAllBase(Model model) {
        model.addAttribute("all", baseService.getAllBase());
        return "all";
    }

    @PostMapping("/main/edit/{id}")
    public String editBase(@PathVariable("id")int id,
                           @ModelAttribute("base") Base baseModel,
                           Model model) {
        System.out.println("Меня меняю");
        baseService.createOrUpdateBase(baseModel);
        return "edit";
    }

    @GetMapping("/main/edit/{id}")
    public String editBase(@ModelAttribute("base") Base baseModel) {
        System.out.println("Меня меняю1");
        return "edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id,
                         @ModelAttribute("base") Base baseModel){
        baseService.deleteBaseById(id);
        return "main";
    }

    @GetMapping("/main/all/completed")
    public String getAllBaseComplete(Model model) {
        model.addAttribute("all", baseService.findByActive(true));
        return "all";
    }

    @GetMapping("/main/all/notcompleted")
    public String getAllBaseNotComplete(Model model) {
        model.addAttribute("all", baseService.findByActive(false));
        return "all";
    }
}
