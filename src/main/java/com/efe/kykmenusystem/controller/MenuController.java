package com.efe.kykmenusystem.controller;

import com.efe.kykmenusystem.dto.MenuForm;
import com.efe.kykmenusystem.dto.MenuSearchCriteria;
import com.efe.kykmenusystem.entity.MealType;
import com.efe.kykmenusystem.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public String listMenus(@ModelAttribute("search") MenuSearchCriteria searchCriteria, Model model) {
        model.addAttribute("menus", menuService.searchMenus(searchCriteria));
        model.addAttribute("mealTypes", MealType.values());
        return "menu-list";
    }

    @GetMapping("/new")
    public String newMenuForm(Model model) {
        prepareFormModel(model, new MenuForm(), true, null);
        return "menu-form";
    }

    @PostMapping
    public String createMenu(@Valid @ModelAttribute("menuForm") MenuForm menuForm,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            prepareFormModel(model, menuForm, true, null);
            return "menu-form";
        }

        menuService.create(menuForm);
        return "redirect:/menus";
    }

    @GetMapping("/{id}")
    public String menuDetail(@PathVariable Long id, Model model) {
        model.addAttribute("menu", menuService.findById(id));
        return "menu-detail";
    }

    @GetMapping("/{id}/edit")
    public String editMenuForm(@PathVariable Long id, Model model) {
        var menu = menuService.findById(id);
        prepareFormModel(model, MenuForm.fromEntity(menu), false, menu.getImageData() != null);
        return "menu-form";
    }

    @PostMapping("/{id}")
    public String updateMenu(@PathVariable Long id,
                             @Valid @ModelAttribute("menuForm") MenuForm menuForm,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            var currentMenu = menuService.findById(id);
            prepareFormModel(model, menuForm, false, currentMenu.getImageData() != null);
            return "menu-form";
        }

        menuService.update(id, menuForm);
        return "redirect:/menus/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteMenu(@PathVariable Long id) {
        menuService.delete(id);
        return "redirect:/menus";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getMenuImage(@PathVariable Long id) {
        return menuService.getMenuImage(id);
    }

    private void prepareFormModel(Model model, MenuForm menuForm, boolean createMode, Boolean hasImage) {
        model.addAttribute("menuForm", menuForm);
        model.addAttribute("mealTypes", MealType.values());
        model.addAttribute("createMode", createMode);
        model.addAttribute("hasImage", Boolean.TRUE.equals(hasImage));
    }
}
