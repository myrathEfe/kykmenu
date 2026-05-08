package com.efe.kykmenusystem.controller;

import com.efe.kykmenusystem.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class DashboardController {

    private static final DateTimeFormatter DASHBOARD_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yy");

    private final MenuService menuService;

    public DashboardController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(value = "forbidden", required = false) String forbidden, Model model) {
        model.addAttribute("totalMenuCount", menuService.countAllMenus());
        model.addAttribute("todayMenuCount", menuService.countTodayMenus());
        model.addAttribute("todayMenus", menuService.findTodayMenus());
        model.addAttribute("formattedTodayDate", LocalDate.now().format(DASHBOARD_DATE_FORMAT));
        model.addAttribute("forbiddenMessage", forbidden != null
                ? "Bu işlem için yetkiniz bulunmuyor."
                : null);
        return "dashboard";
    }
}
