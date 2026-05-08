package com.efe.kykmenusystem.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException ex, Model model, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        model.addAttribute("statusCode", 404);
        model.addAttribute("errorTitle", "Kayıt Bulunamadı");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        model.addAttribute("statusCode", 500);
        model.addAttribute("errorTitle", "Beklenmeyen Bir Hata Oluştu");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
