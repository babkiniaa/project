package org.example.controller;

import org.example.Enums.Category;
import org.example.entity.ArchiveTask;
import org.example.entity.Base;
import org.example.service.ArchiveService;
import org.example.service.BaseService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public String getAllUsers(Model model) {
        model.addAttribute("base", new Base());
        return "/bases/main";
    }


    @PostMapping("/main")
    public String addProduct(@ModelAttribute("base") Base baseModel, Model model) {
        baseService.createOrUpdateBase(baseModel);
        return "/bases/main";
    }

    @GetMapping("/main/{id}")
    public String getById(@PathVariable int id, Model model) {
        Base base = baseService.getBaseById(id);
        model.addAttribute("task", base);
        return "/bases/info";
    }

    @GetMapping("/main/all")
    public String getAllBase(Model model) {
        model.addAttribute("all", baseService.getAllBase());
        return "/bases/all";
    }

    @PostMapping("/main/edit/{id}")
    public String editBase(@PathVariable("id") int id,
                           @ModelAttribute("base") Base baseModel,
                           Model model) {
        baseService.createOrUpdateBase(baseModel);
        return "/bases/edit";
    }

    @GetMapping("/main/edit/{id}")
    public String editBase(@ModelAttribute("base") Base baseModel) {
        return "/bases/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id,
                         @ModelAttribute("base") Base baseModel) {
        baseService.deleteBaseById(id);
        return "/bases/all";
    }

    @GetMapping("/main/all/completed")
    public String getAllBaseComplete(Model model) {
        model.addAttribute("all", baseService.findByActive(true));
        return "/bases/all";
    }

    @GetMapping("/main/all/notcompleted")
    public String getAllBaseNotComplete(Model model) {
        model.addAttribute("all", baseService.findByActive(false));
        return "/bases/all";
    }

    @GetMapping("/main/all/forTime")
    public String getAllBaseTime(Model model, @RequestParam(required = false) LocalDateTime date) {
        model.addAttribute("all", baseService.findByTime(date));
        return "/bases/all";
    }

    @GetMapping("/main/all/forCategory")
    public String getAllForCategory(Model model, @RequestParam(required = false)Category category) {
        model.addAttribute("all", baseService.findByCategory(category));
        return "/bases/all";
    }

    @GetMapping("/main/all/forName")
    public String getAllBaseName(Model model, @RequestParam(required = false) String search) {
        System.out.println(search);
        model.addAttribute("all", baseService.findByName(search));
        return "/bases/all";
    }

    @GetMapping("archive/{id}")
    public String archived(@PathVariable int id, @ModelAttribute("base") Base baseModel) {
        Base base = baseService.getBaseById(id);
        ArchiveTask archiveTask = new ArchiveTask(base.getId(), base.getName(), base.getDescription(), base.getTime(), base.getActive(), base.getRating(), base.getCategory(), base.getRepeatable());
        baseService.deleteBaseById(id);
        archiveService.createOrUpdateArchive(archiveTask);
        return "redirect:/bases/main/all";
    }

    @GetMapping("main/all/all")
    public String allAll(Model model) {
        List<Base> bases = baseService.getAllBase();
        List<ArchiveTask> archiveTasks = archiveService.getAllArchive();
        model.addAttribute("all", bases);
        model.addAttribute("archive", archiveTasks);
        return "/bases/all";
    }

    @GetMapping("main/all/allSort")
    public String AllSort(Model model) {
        List<Base> bases = baseService.sortByRating();
        model.addAttribute("all", bases);
        return "/bases/all";
    }
    @GetMapping("/main/archive/{id}")
    public String getByIdArchive(@PathVariable int id, Model model) {
        ArchiveTask archiveTask = archiveService.getArchiveById(id);
        model.addAttribute("task", archiveTask);
        return "/bases/info";
    }

    @Scheduled(initialDelay = 2000, fixedRate = 3000)
    @Async
    public void nextTime(){
        List<Base> bases = baseService.findByTime(LocalDateTime.now());
        baseService.nextTime(bases);
    }
}
