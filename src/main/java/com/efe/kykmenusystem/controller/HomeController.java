package com.efe.kykmenusystem.controller;

import com.efe.kykmenusystem.config.SessionConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session) {
        return session.getAttribute(SessionConstants.USER_ID) != null
                ? "redirect:/dashboard"
                : "redirect:/login";
    }
}
