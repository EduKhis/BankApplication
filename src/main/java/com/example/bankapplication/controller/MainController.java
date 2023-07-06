package com.example.bankapplication.controller;

import com.example.bankapplication.exchange.Exchange;
import com.example.bankapplication.model.*;
import com.example.bankapplication.repository.HistoryRepository;
import com.example.bankapplication.service.CardService;
import com.example.bankapplication.service.HistoryService;
import com.example.bankapplication.util.CardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    CardService cardService;
    @Autowired
    HistoryService historyService;
    @Autowired
    HistoryRepository historyRepository;

    @GetMapping("personalPage")
    public String openPersonalPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("cards", cardService.findCreditCardById(user));
        return "homePage";
    }

    @GetMapping("newCard")
    public String createCard(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("number", cardService.getValidCardNumber());
        model.addAttribute("username", user.getName() + " " + user.getSurname());
        model.addAttribute("date", CardUtil.cardDate());
        model.addAttribute("options", Currency.values());
        model.addAttribute("creditcard", new CreditCard());

        return "createCard";
    }

    @PostMapping("addCard")
    public String saveCard(@AuthenticationPrincipal User user, @ModelAttribute("creditcard") CreditCard card,
                           @RequestParam(name = "teg") String teg) {
        cardService.save(card, teg, user);
        return "redirect:/personalPage";
    }

    @GetMapping("/cardPage/{id}")
    public String personalCardPage(@PathVariable("id") Long a, Model model) throws IOException {
        List<History>historyList=historyRepository.findByCardReceiverOrCardSender(a,a);
        List<HistoryDto> historyDtoList=historyService.historyList(historyList);
        System.out.println(historyDtoList);
        System.out.println(historyList);
        model.addAttribute("histories", historyDtoList);

        CreditCard card = cardService.findById(a);
        model.addAttribute("number", card.getNumber());
        model.addAttribute("balance", card.getBalance());
        model.addAttribute("options", Currency.values());
        model.addAttribute("currency", card.getCurrency());
        model.addAttribute("listCurrency", Exchange.getExchangeRate().entrySet());
        model.addAttribute("id", a);

        return "cardPage";
    }
    @PostMapping("putBalance")
    public String putBalance (Model model, @RequestParam(name = "add", required = false) String add, @RequestParam(name = "id") Long id,
                              @RequestParam(name = "teg") String teg) throws IOException {
        CreditCard card = cardService.findById(id);
        model.addAttribute("answer", "");
        System.out.println(123);

        if (!cardService.putBalance(id, add,teg)) {
            model.addAttribute("answer", "incorrect values");
        }
        model.addAttribute("number", card.getNumber());
        model.addAttribute("balance", card.getBalance());
        model.addAttribute("options", Currency.values());
        model.addAttribute("currency", card.getCurrency());
        model.addAttribute("listCurrency", Exchange.getExchangeRate().entrySet());
        model.addAttribute("id", id);

        List<History>historyList=historyRepository.findByCardReceiverOrCardSender(id,id);
        List<HistoryDto> historyDtoList=historyService.historyList(historyList);
        System.out.println(historyDtoList);
        System.out.println(historyList);
        model.addAttribute("histories", historyDtoList);

        return "cardPage";
    }


    @PostMapping("checkNumber")
    public String sendMoney (Model model, @RequestParam(name = "id") Long id, @RequestParam(name = "sendNumber") String sendNumber) throws IOException {
        model.addAttribute("answer2", "");
        if (!cardService.checkNumber(sendNumber)) {
            model.addAttribute("answer2", "incorrect number");
            return "redirect:/cardPage/"+ id;
        }
        CreditCard takeCard = cardService.findCreditCardByNumber(sendNumber);
        model.addAttribute("name", cardService.getNameSurname(takeCard));
        model.addAttribute("number", takeCard.getNumber());
        model.addAttribute("options", Currency.values());
        model.addAttribute("id", id);
        return "transfer";
    }
    @PostMapping("sendMoney")
    public String sendMoney (Model model, @RequestParam(name = "id") Long id, @RequestParam(name = "number") String sendNumber,
                             @RequestParam(name = "add", required = false) String add, @RequestParam(name = "teg") String teg) throws IOException {
        CreditCard takeCard = cardService.findCreditCardByNumber(sendNumber);
        model.addAttribute("answer", "");
        if (!cardService.transferBalance(id, add,teg,sendNumber)) {
            model.addAttribute("answer", "incorrect values or put balance");
            return "redirect:/cardPage/"+ id;
            //return "transfer";
        }
        model.addAttribute("name", cardService.getNameSurname(takeCard));
        model.addAttribute("number", takeCard.getNumber());
        model.addAttribute("options", Currency.values());
        model.addAttribute("answer", "money transfer is done");
        return "transfer";
    }



    @GetMapping("example")
    public String ex (){
        return "example";
    }
}
