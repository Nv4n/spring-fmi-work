package com.spring.bank.web;

import com.spring.bank.model.BankAccount;
import com.spring.bank.model.Transaction;
import com.spring.bank.model.User;
import com.spring.bank.service.BankAccountService;
import com.spring.bank.service.TransactionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;
    private BankAccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, BankAccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }


    @GetMapping("/add")
    public String getAddAccountForm(Model transaction, HttpSession session) {
        if (!transaction.containsAttribute("transaction")) {
            transaction.addAttribute("transaction", new Transaction());
        }
        if (!transaction.containsAttribute("fromAccounts")) {
            User owner = (User) session.getAttribute("user");
            Collection<BankAccount> bankAccounts = accountService.getBankAccountsByOwnerId(owner.getId());
            if (bankAccounts.isEmpty()) {
                return "redirect:/";
            }
            transaction.addAttribute("fromAccounts", bankAccounts);
        }
        if (!transaction.containsAttribute("toAccounts")) {
            Collection<BankAccount> toAccounts = new ArrayList<>();
            transaction.addAttribute("toAccounts", toAccounts);
        }
        if (!transaction.containsAttribute("usernameToAccounts")) {
            transaction.addAttribute("usernameToAccounts", "");
        }

        return "add-transaction";
    }

    @PostMapping("/add")
    public String createNewAccount(@Valid @ModelAttribute("transaction") Transaction transaction, final BindingResult binding, RedirectAttributes redirectAttributes) {
        if (binding.hasErrors()) {
            List<String> errorMessages = binding.getAllErrors().stream().map(err -> {
                ConstraintViolation cv = err.unwrap(ConstraintViolation.class);
                return String.format("Err in '%s - invalid value: '%s'", cv.getPropertyPath(), cv.getInvalidValue());
            }).toList();
            log.error("Error creating account: {}", errorMessages);
            redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
            redirectAttributes.addFlashAttribute("transaction", transaction);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.transaction", binding);
            return "redirect:add";
        }

        try {
            log.info("POST Transaction: " + transaction);
            transactionService.createTransaction(transaction);
        } catch (Exception ex) {
            log.error("Error creating account", ex);
            redirectAttributes.addFlashAttribute("errorMessages", "Server error please try again...");
            redirectAttributes.addFlashAttribute("transaction", transaction);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.transaction", binding);
            return "redirect:add";
        }

        return "redirect:/";
    }

    @PostMapping("/add/process")
    public String processAvailableAccounts(@ModelAttribute("transaction") Transaction transaction, @RequestParam("usernameToAccounts") String usernameToAccounts, RedirectAttributes redirectAttributes) {
        if (usernameToAccounts.isBlank()) {
            redirectAttributes.addFlashAttribute("transaction", transaction);
            redirectAttributes.addFlashAttribute("usernameToAccounts", "");
            redirectAttributes.addFlashAttribute("errorMessages", "Transferring to no user is not allowed");
            redirectAttributes.addFlashAttribute("toAccounts", new ArrayList<BankAccount>());
            return "redirect:add";
        }

        Collection<BankAccount> toAccounts = accountService.getBankAccountsByOwnerUsername(usernameToAccounts);
        if (toAccounts.isEmpty()) {
            redirectAttributes.addFlashAttribute("transaction", transaction);
            redirectAttributes.addFlashAttribute("usernameToAccounts", usernameToAccounts);
            redirectAttributes.addFlashAttribute("errorMessages", "No bank accounts were found");
            redirectAttributes.addFlashAttribute("toAccounts", new ArrayList<BankAccount>());
            return "redirect:add";
        }

        redirectAttributes.addFlashAttribute("transaction", transaction);
        redirectAttributes.addFlashAttribute("usernameToAccounts", usernameToAccounts);
        redirectAttributes.addFlashAttribute("errorMessages", "No bank accounts were found");
        redirectAttributes.addFlashAttribute("toAccounts", toAccounts);
        return "redirect:add";
    }
}
