package com.invform.invform.controller;
import com.invform.invform.service.UserService;
import com.invform.invform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User("", "", ""));
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return "redirect:/users";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/users/edit/{username}")
    public String showEditForm(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/users/edit")
    public String editUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{username}")
    public String deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return "redirect:/users";
    }
}