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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
    public String createNewAccount(@Valid @ModelAttribute("transaction") Transaction transaction,
                                   @ModelAttribute("usernameToAccounts") String usernameToAccounts,
                                   @ModelAttribute("toAccounts") ArrayList<BankAccount> toAccounts,
                                   final BindingResult binding, RedirectAttributes redirectAttributes) {
        if (binding.hasErrors()) {
            List<String> errorMessages = binding.getAllErrors().stream().map(err -> {
                ConstraintViolation cv = err.unwrap(ConstraintViolation.class);
                return String.format("Err in '%s - invalid value: '%s'", cv.getPropertyPath(), cv.getInvalidValue());
            }).toList();
            log.error("Error creating account: {}", errorMessages);
            return redirectInvalidTransaction(transaction, usernameToAccounts, toAccounts,
                    redirectAttributes, errorMessages.toString());
        }
        if (transaction.getReceiverId() == null) {
            return redirectInvalidTransaction(transaction, usernameToAccounts, toAccounts,
                    redirectAttributes, "Not existing receiver");
        }
        if (transaction.getSenderId() == null) {
            return redirectInvalidTransaction(transaction, usernameToAccounts, toAccounts,
                    redirectAttributes, "Not existing sender");
        }

        if (transaction.getReceiverId().equals(transaction.getSenderId())) {
            return redirectInvalidTransaction(transaction, usernameToAccounts, toAccounts,
                    redirectAttributes, "Can't transfer from to same bank account");
        }
        BankAccount sender;
        BankAccount receiver;
        try {
            sender = accountService.getBankAccountById(transaction.getSenderId());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return redirectInvalidTransaction(transaction, usernameToAccounts, toAccounts,
                    redirectAttributes, "Can't find sender's bank account");
        }
        try {
            receiver = accountService.getBankAccountById(transaction.getReceiverId());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return redirectInvalidTransaction(transaction, usernameToAccounts, toAccounts,
                    redirectAttributes, "Can't find receiver's bank account");
        }
        if (sender.getBalance() < transaction.getAmount()) {
            log.error(String.format("Insufficient funds: %.2f", sender.getBalance()));
            return redirectInvalidTransaction(transaction, usernameToAccounts, toAccounts,
                    redirectAttributes, "Insufficient funds");
        }
        try {
            log.info("POST Transaction: " + transaction);
            transaction.setCreatedAt(new Date());
            transaction.setSender(sender);
            transaction.setReceiver(receiver);
            transactionService.createTransaction(transaction);
        } catch (Exception ex) {
            log.error("Error creating account", ex);
            return redirectInvalidTransaction(transaction, usernameToAccounts, toAccounts,
                    redirectAttributes, "Server error please try again...");
        }

        return "redirect:/";
    }

    private static String redirectInvalidTransaction(Transaction transaction, String usernameToAccounts, Collection<BankAccount> toAccounts,
                                                     RedirectAttributes redirectAttributes, String errorMessages) {
        redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
        redirectAttributes.addFlashAttribute("transaction", transaction);
        redirectAttributes.addFlashAttribute("toAccounts", toAccounts);
        redirectAttributes.addFlashAttribute("usernameToAccounts", usernameToAccounts);
        return "redirect:add";
    }

    @PostMapping("/add/process")
    public String processAvailableAccounts(@ModelAttribute("transaction") Transaction transaction, @ModelAttribute("usernameToAccounts") String usernameToAccounts, RedirectAttributes redirectAttributes) {
        log.info(usernameToAccounts);
        log.info(transaction.toString());
        if (usernameToAccounts.isBlank()) {
            redirectAttributes.addFlashAttribute("transaction", transaction);
            redirectAttributes.addFlashAttribute("usernameToAccounts", "");
            redirectAttributes.addFlashAttribute("errorMessages", "Transferring to no user is not allowed");
            redirectAttributes.addFlashAttribute("toAccounts", new ArrayList<BankAccount>());
            return "redirect:/transactions/add";
        }

        Collection<BankAccount> toAccounts = accountService.getBankAccountsByOwnerUsername(usernameToAccounts);
        if (toAccounts.isEmpty()) {
            redirectAttributes.addFlashAttribute("transaction", transaction);
            redirectAttributes.addFlashAttribute("usernameToAccounts", usernameToAccounts);
            redirectAttributes.addFlashAttribute("errorMessages", "No bank accounts were found");
            redirectAttributes.addFlashAttribute("toAccounts", new ArrayList<BankAccount>());
            return "redirect:/transactions/add";
        }

        redirectAttributes.addFlashAttribute("transaction", transaction);
        redirectAttributes.addFlashAttribute("usernameToAccounts", usernameToAccounts);
        redirectAttributes.addFlashAttribute("errorMessages", null);
        redirectAttributes.addFlashAttribute("toAccounts", toAccounts);
        log.info(String.format("ACCOUNTS: %s", toAccounts));
        return "redirect:/transactions/add";
    }
}
