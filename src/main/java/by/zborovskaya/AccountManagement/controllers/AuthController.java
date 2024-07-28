package by.zborovskaya.AccountManagement.controllers;

import by.zborovskaya.AccountManagement.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/main")
    public String getAccountsPage(Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        String role = personDetails.getPerson().getRole();
        if(role.equals("ROLE_ADMIN")){
            return "redirect:/accounts/admin";
        } else{
            return "redirect:/accounts/user";
        }
    }
}
