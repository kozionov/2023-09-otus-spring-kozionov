package ru.otus.hw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.hw.dto.UserRegisterDto;
import ru.otus.hw.services.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    @Autowired
    private UserService userService;
    @ModelAttribute("user")
    public UserRegisterDto userRegistrationDto(){
        return new UserRegisterDto();
    }
    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegisterDto registrationDto) {
        userService.save(registrationDto);
        return "redirect:/registration?success";
    }
}
