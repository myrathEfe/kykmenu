package com.efe.kykmenusystem.controller;

import com.efe.kykmenusystem.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {

    private final MenuService menuService;

    public DashboardController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(value = "forbidden", required = false) String forbidden, Model model) {
        model.addAttribute("totalMenuCount", menuService.countAllMenus());
        model.addAttribute("todayMenuCount", menuService.countTodayMenus());
        model.addAttribute("todayMenus", menuService.findTodayMenus());
        model.addAttribute("forbiddenMessage", forbidden != null
                ? "Bu işlem için yetkiniz bulunmuyor."
                : null);
        return "dashboard";
    }
}
