package com.spring.bank.web;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getAddAccountForm(Model transaction) {
        if (!transaction.containsAttribute("transaction")) {
            transaction.addAttribute("transaction", new Transaction());
        }
        return "add-transaction";
    }

    @PostMapping("/add")
    public String createNewAccount(@Valid @ModelAttribute("transaction") Transaction transaction, final BindingResult binding, RedirectAttributes redirectAttributes,
                                   HttpServletRequest request, HttpSession session) {
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
            transaction.setOwner((User) session.getAttribute("user"));
            transaction.setBalance(0.0);
            log.info("POST Account: " + transaction);
            accountService.createBankAccount(transaction);
        } catch (Exception ex) {
            log.error("Error creating account", ex);
            redirectAttributes.addFlashAttribute("errorMessages", "Server error please try again...");
            redirectAttributes.addFlashAttribute("account", transaction);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.account", binding);
            return "redirect:add";
        }

        return "redirect:/";
    }
}
