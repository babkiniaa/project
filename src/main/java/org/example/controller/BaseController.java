package org.example.controller;

import jakarta.validation.Valid;
import org.example.Enums.Category;
import org.example.entity.ArchiveTask;
import org.example.entity.Base;
import org.example.entity.User;
import org.example.service.ArchiveService;
import org.example.service.BaseService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class BaseController {
    private UserService userService;
    private BaseService baseService;
    private ArchiveService archiveService;

    @Autowired
    public BaseController(UserService userService, BaseService baseService, ArchiveService archiveService) {
        this.userService = userService;
        this.baseService = baseService;
        this.archiveService = archiveService;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("base", new Base());
        return "main";
    }

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("base", new Base());
        return "main";
    }

    @PostMapping("/main")
    public String addProduct(@Valid @ModelAttribute("base") Base baseModel, BindingResult bindingResult, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            return "main";
        }
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
        baseModel.setUser(user);
        baseService.createOrUpdateBase(baseModel, user);
        return "main";
    }

    @GetMapping("/main/{id}")
    public String getById(@PathVariable int id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
        Base base = baseService.getBaseById(id, user);
        model.addAttribute("task", base);
        return "info";
    }

    @GetMapping("/main/all")
    public String getAllBase(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("all", baseService.getAllBase(user));
        return "all";
    }

    @PostMapping("/main/edit/{id}")
    public String editBase(@PathVariable("id") int id,
                           @Valid @ModelAttribute("base") Base baseModel, BindingResult bindingResult,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        System.out.println(12121212);
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
        baseService.createOrUpdateBase(baseModel, user);
        return "edit";
    }

    @GetMapping("/main/edit/{id}")
    public String editBase(@ModelAttribute("base") Base baseModel) {
        return "/edit";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id,
                         @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
        baseService.deleteBaseById(id, user);
        return "all";
    }

    @GetMapping("/main/all/completed")
    public String getAllBaseComplete(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("all", baseService.findByActive(true, user));
        return "all";
    }

    @GetMapping("/main/all/notcompleted")
    public String getAllBaseNotComplete(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("all", baseService.findByActive(false, user));
        return "all";
    }

    @GetMapping("/main/all/forTime")
    public String getAllBaseTime(Model model, @Valid @RequestParam(required = false) LocalDateTime date, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            return "main";
        }
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("all", baseService.findByTime(date, user));
        return "all";
    }

    @GetMapping("/main/all/forCategory")
    public String getAllForCategory(Model model, @RequestParam(required = false) Category category, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("all", baseService.findByCategory(category, user));
        return "all";
    }

    @GetMapping("/main/all/forName")
    public String getAllBaseName(Model model, @RequestParam(required = false) String search, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("all", baseService.findByName(search, user));
        return "all";
    }

    @GetMapping("archive/{id}")
    public String archived(@PathVariable int id, @ModelAttribute("base") Base baseModel, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
        Base base = baseService.getBaseById(id, user);
        ArchiveTask archiveTask = new ArchiveTask(base.getId(), base.getName(), base.getDescription(), base.getTime(), base.getActive(), base.getRating(), base.getCategory(), base.getRepeatable(), base.getUser());
        baseService.deleteBaseById(id, user);
        archiveService.createOrUpdateArchive(archiveTask, user);
        return "all";
    }

    @GetMapping("main/all/all")
    public String allAll(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
        List<Base> bases = baseService.getAllBase(user);
        List<ArchiveTask> archiveTasks = archiveService.getAllArchive(user);
        model.addAttribute("all", bases);
        model.addAttribute("archive", archiveTasks);
        return "all";
    }

    @GetMapping("main/all/allSort")
    public String AllSort(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
        List<Base> bases = baseService.sortByRating(user);
        model.addAttribute("all", bases);
        return "all";
    }

    @GetMapping("/main/archive/{id}")
    public String getByIdArchive(@PathVariable int id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
        ArchiveTask archiveTask = archiveService.getArchiveById(id,user);
        model.addAttribute("task", archiveTask);
        return "info";
    }

    @Scheduled(initialDelay = 2000, fixedRate = 3000)
    @Async
    public void nextTime(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow();
        List<Base> bases = baseService.findByTime(LocalDateTime.now(), user);
        baseService.nextTime(bases);
    }
}
