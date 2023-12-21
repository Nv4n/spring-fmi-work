package com.spring.bank.web;

import com.spring.bank.model.User;
import com.spring.bank.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

@Controller
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "auth-register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid @ModelAttribute("user") User user, final BindingResult binding, RedirectAttributes redirectAttributes) {
        if (binding.hasErrors()) {
            log.error("Error registering user: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "user", binding);
            return "redirect:register";
        }
        try {
            authService.register(user);
        } catch (Exception ex) {
            log.error("Error registering user", ex);
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "user", binding);
            return "redirect:register";
        }

        return "redirect:login";
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        if (!model.containsAttribute("username")) {
            model.addAttribute("username", "");
        }
        if (!model.containsAttribute("password")) {
            model.addAttribute("password", "");
        }

        return "auth-login";
    }


    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @ModelAttribute("redirectUrl") String redirectUrl,
                            RedirectAttributes redirectAttributes,
                            HttpSession session) {
        try {
            User loggedUser = authService.login(username, password);
            if (loggedUser == null) {
                return getInvalidLoginUserResponse(username, redirectUrl, redirectAttributes);
            }
            session.setAttribute("user", loggedUser);
            if (redirectUrl != null && redirectUrl.length() > 0) {
                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            return getInvalidLoginUserResponse(username, redirectUrl, redirectAttributes);
        }

        return "redirect:/";
    }

    private static String getInvalidLoginUserResponse(String username, String redirectUrl, RedirectAttributes redirectAttributes) {
        String errors = "Invalid username or password.";
        redirectAttributes.addFlashAttribute("username", username);
        redirectAttributes.addFlashAttribute("errors", errors);
        redirectAttributes.addAttribute("redirectUrl", redirectUrl);
        return "redirect:login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
