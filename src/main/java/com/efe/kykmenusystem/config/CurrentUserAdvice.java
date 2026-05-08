package com.efe.kykmenusystem.config;

import com.efe.kykmenusystem.entity.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserAdvice {

    @ModelAttribute("authenticated")
    public boolean authenticated(HttpSession session) {
        return session.getAttribute(SessionConstants.USER_ID) != null;
    }

    @ModelAttribute("currentUsername")
    public String currentUsername(HttpSession session) {
        Object username = session.getAttribute(SessionConstants.USERNAME);
        return username != null ? username.toString() : null;
    }

    @ModelAttribute("currentRole")
    public Role currentRole(HttpSession session) {
        Object role = session.getAttribute(SessionConstants.USER_ROLE);
        return role instanceof Role currentRole ? currentRole : null;
    }
}
