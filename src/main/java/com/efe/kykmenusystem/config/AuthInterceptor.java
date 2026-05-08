package com.efe.kykmenusystem.config;

import com.efe.kykmenusystem.entity.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.regex.Pattern;

@Component
public class AuthInterceptor implements org.springframework.web.servlet.HandlerInterceptor {

    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/login",
            "/logout",
            "/error"
    );

    private static final Pattern MENU_EDIT_PATTERN = Pattern.compile("^/menus/\\d+/edit$");
    private static final Pattern MENU_UPDATE_PATTERN = Pattern.compile("^/menus/\\d+$");
    private static final Pattern MENU_DELETE_PATTERN = Pattern.compile("^/menus/\\d+/delete$");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();

        if (isPublicPath(path)) {
            return true;
        }

        Object roleObject = request.getSession().getAttribute(SessionConstants.USER_ROLE);
        if (roleObject == null) {
            response.sendRedirect("/login?authRequired");
            return false;
        }

        if (isAdminOnlyPath(path, request.getMethod()) && roleObject != Role.ADMIN) {
            response.sendRedirect("/dashboard?forbidden");
            return false;
        }

        return true;
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.contains(path)
                || path.startsWith("/css/")
                || path.startsWith("/js/");
    }

    private boolean isAdminOnlyPath(String path, String method) {
        return path.equals("/menus/new")
                || ("POST".equalsIgnoreCase(method) && path.equals("/menus"))
                || MENU_EDIT_PATTERN.matcher(path).matches()
                || ("POST".equalsIgnoreCase(method) && MENU_UPDATE_PATTERN.matcher(path).matches())
                || MENU_DELETE_PATTERN.matcher(path).matches();
    }
}
