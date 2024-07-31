package by.zborovskaya.AccountManagement.controllers;

import by.zborovskaya.AccountManagement.security.PersonDetails;
import by.zborovskaya.AccountManagement.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accounts")
public class AccountsController {
    private final AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("accounts", accountService.findAll(true));
        return "accounts/admin";
    }

    @PostMapping("/admin/{id}/changeStatus")
    public String changeStatus(@PathVariable int id) {
        accountService.changeStatus(id);
        return "redirect:/accounts/admin";
    }

    @GetMapping("/user")
    public String getUserAccount(Authentication authentication, Model model) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id = personDetails.getPerson().getId();
        model.addAttribute("accounts", accountService.findByPerson(personDetails.getPerson()));

        return "accounts/user";
    }

    @PostMapping("/user/{id}/editPage")
    public String getEditPage(@PathVariable int id, Model model, @RequestParam(value = "operation") String operationType) {
        model.addAttribute("account", accountService.findOne(id));
        model.addAttribute("operation", operationType);
        return "accounts/edit";
    }

    @PostMapping("/user/edit")
    public String update(@RequestParam(value = "operation") String operationType,
                         @RequestParam(value = "accountNumber") int number,
                         @RequestParam(value = "value") int value) {
        if(operationType.equals("TOP_UP")){
            accountService.topUpAccount(number, value);
        }else{
            accountService.withdrawMoney(number, value);
        }
        return "redirect:/accounts/user";
    }

}
