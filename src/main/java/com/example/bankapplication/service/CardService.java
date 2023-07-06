package com.example.bankapplication.service;

import com.example.bankapplication.exchange.Exchange;
import com.example.bankapplication.model.CreditCard;
import com.example.bankapplication.model.Currency;
import com.example.bankapplication.model.User;
import com.example.bankapplication.repository.CardRepository;
import com.example.bankapplication.repository.UserRepository;
import com.example.bankapplication.util.CardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    HistoryService historyService;

    public void save(CreditCard card, String teg, User user) {
        card.setBalance(new BigDecimal(0));
        card.setCurrency(Currency.valueOf(teg));
        card.setUser(user);
        cardRepository.save(card);
    }

    public List<CreditCard> findCreditCardById(User user) {
        return cardRepository.findByUser(user);
    }

    public String getValidCardNumber() {
        String number = CardUtil.generateCardNum();
        if (cardRepository.existsByNumber(number)) {
            getValidCardNumber();
        }
        return number;

    }

    public CreditCard findById(Long id) {
        return cardRepository.findById(id).get();
    }

    public boolean putBalance(Long id, String coin, String currency) throws IOException {
        if (!coin.matches("[0-9]+")) {
            return false;
        }
        else {
            CreditCard card = cardRepository.findById(id).get();
            BigDecimal bigCoin = new BigDecimal(coin);  //пополняем
            BigDecimal k = Exchange.convertRate(currency, String.valueOf(card.getCurrency()));  //коэф
            BigDecimal total = bigCoin.multiply(k);
            card.setBalance(card.getBalance().add(total));
            System.out.println(card.getBalance());
            historyService.addHistory(card,card, Currency.valueOf(currency), bigCoin);
            cardRepository.save(card);
            return true;
        }

    }
    public boolean transferBalance(Long id, String coin, String currency, String number) throws IOException {
        if (!coin.matches("[0-9]+")) {
            return false;
        }
        else {
            CreditCard cardSender = cardRepository.findById(id).get();
            CreditCard cardReceiver = cardRepository.findByNumber(number);
            BigDecimal bigCoin = new BigDecimal(coin);  //перевели в BigDecimal перводимые деньги
                System.out.println(bigCoin);
            BigDecimal k2 = Exchange.convertRate(currency,String.valueOf(cardSender.getCurrency()));  //коэф курса
                System.out.println(k2);
            BigDecimal total2 = bigCoin.multiply(k2);
            if (total2.doubleValue()> cardSender.getBalance().doubleValue()){
                return false;
            }
                System.out.println(total2);
            cardSender.setBalance(cardSender.getBalance().subtract(total2));
            cardRepository.save(cardReceiver);
            BigDecimal k = Exchange.convertRate((String.valueOf(cardSender.getCurrency())), String.valueOf(cardReceiver.getCurrency()));  //коэф курса
                System.out.println(k);
            BigDecimal total = total2.multiply(k); //то что будем класть в кошелек
            historyService.addHistory(cardSender,cardReceiver, Currency.valueOf(currency), bigCoin);
                System.out.println(total);
            cardReceiver.setBalance(cardReceiver.getBalance().add(total));
            cardRepository.save(cardSender);
            return true;
        }
    }
    public boolean checkNumber (String number) {
        return cardRepository.existsByNumber(number);
    }


    public CreditCard findCreditCardByNumber(String number) {
        return cardRepository.findByNumber(number);
    }
    public String getNameSurname (CreditCard card) {
        return card.getUser().getName() + " " + card.getUser().getSurname();
    }

}
