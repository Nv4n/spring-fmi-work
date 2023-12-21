package com.spring.bank.web;

import com.spring.bank.model.BankAccount;
import com.spring.bank.model.Transaction;
import com.spring.bank.model.User;
import com.spring.bank.service.BankAccountService;
import com.spring.bank.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
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

import java.util.*;

@Controller
@Slf4j
@RequestMapping("/accounts")
public class BankAccountController {

    private TransactionService transactionService;
    private BankAccountService accountService;

    @Autowired
    public BankAccountController(TransactionService transactionService, BankAccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping
    public String getAccounts(Model accounts, @RequestParam(value = "limit", defaultValue = "10") String limit, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Collection<BankAccount> bankAccounts = accountService.getBankAccountsByOwnerId(user.getId());
        accounts.addAttribute("accounts", bankAccounts);
        List<String> options = new ArrayList<>();
        options.add("10");
        options.add("20");
        options.add("50");
        accounts.addAttribute("limitOptions", options);

        Map<String, Collection<Transaction>> transactionsByAccount = new TreeMap<>();

        int limitNumber = 10;
        try {
            limitNumber = Integer.parseInt(limit);
        } catch (NumberFormatException ignored) {
        }
        log.info(String.format("Account Limit: %s", limit));
        for (BankAccount ba : bankAccounts) {
            Collection<Transaction> transactions = transactionService.getTransactionsByAccountIdWithLimit(ba.getId(), limitNumber);
            transactionsByAccount.put(ba.getId().toString(), transactions);
        }
        accounts.addAttribute("transactions", transactionsByAccount);
        return "accounts";
    }

    @GetMapping("/add")
    public String getAddAccountForm(Model account) {
        if (!account.containsAttribute("account")) {
            account.addAttribute("account", new BankAccount());
        }
        return "add-account";
    }

    @PostMapping("/add")
    public String createNewAccount(@Valid @ModelAttribute("account") BankAccount account, final BindingResult binding, RedirectAttributes redirectAttributes,
                                   HttpServletRequest request, HttpSession session) {
        if (binding.hasErrors()) {
            List<String> errorMessages = binding.getAllErrors().stream().map(err -> {
                ConstraintViolation cv = err.unwrap(ConstraintViolation.class);
                return String.format("Err in '%s - invalid value: '%s'", cv.getPropertyPath(), cv.getInvalidValue());
            }).toList();
            log.error("Error creating account: {}", errorMessages);
            redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
            redirectAttributes.addFlashAttribute("account", account);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.transaction", binding);
            return "redirect:add";
        }

        try {
            account.setOwner((User) session.getAttribute("user"));
            account.setBalance(0.0);
            log.info("POST Account: " + account);
            accountService.createBankAccount(account);
        } catch (Exception ex) {
            log.error("Error creating account", ex);
            redirectAttributes.addFlashAttribute("errorMessages", "Server error please try again...");
            redirectAttributes.addFlashAttribute("account", account);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.account", binding);
            return "redirect:add";
        }

        return "redirect:/";
    }
}
