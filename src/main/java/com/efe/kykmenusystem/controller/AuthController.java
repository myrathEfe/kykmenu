package com.efe.kykmenusystem.controller;

import com.efe.kykmenusystem.config.SessionConstants;
import com.efe.kykmenusystem.dto.LoginRequest;
import com.efe.kykmenusystem.entity.User;
import com.efe.kykmenusystem.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "authRequired", required = false) String authRequired,
                            @RequestParam(value = "logout", required = false) String logout,
                            HttpSession session,
                            Model model) {
        if (session.getAttribute(SessionConstants.USER_ID) != null) {
            return "redirect:/dashboard";
        }

        if (!model.containsAttribute("loginRequest")) {
            model.addAttribute("loginRequest", new LoginRequest());
        }

        if (authRequired != null) {
            model.addAttribute("warningMessage", "Bu sayfaya erişmek için önce giriş yapmalısınız.");
        }
        if (logout != null) {
            model.addAttribute("successMessage", "Oturumunuz güvenli şekilde kapatıldı.");
        }

        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginRequest") LoginRequest loginRequest,
                        BindingResult bindingResult,
                        HttpSession session,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        User user = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            model.addAttribute("errorMessage", "Kullanıcı adı veya şifre hatalı.");
            return "login";
        }

        session.setAttribute(SessionConstants.USER_ID, user.getId());
        session.setAttribute(SessionConstants.USERNAME, user.getUsername());
        session.setAttribute(SessionConstants.USER_ROLE, user.getRole());

        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}
