package com.example.bankapplication.service;

import com.example.bankapplication.model.CreditCard;
import com.example.bankapplication.model.Currency;
import com.example.bankapplication.model.History;
import com.example.bankapplication.model.HistoryDto;
import com.example.bankapplication.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HistoryService {
    @Autowired
    HistoryRepository historyRepository;

    public void addHistory (CreditCard cardSender, CreditCard cardReceiver, Currency currency, BigDecimal sum) {
        History history =new History();
        history.setCardReceiver(cardReceiver);
        history.setCardSender(cardSender);
        history.setCurrency(currency);
        history.setSum(sum);
        history.setDate(new Date());
        historyRepository.save(history);
    }

    public List<HistoryDto> historyList (List<History> history) {
        List<HistoryDto> historyDtoList = new ArrayList<>();
        for (History i : history) {
            HistoryDto historyDto = new HistoryDto(i.getId(), i.getCardSender().getNumber(),i.getCardReceiver().getNumber(),i.getCurrency(),i.getSum(),i.getDate());
            historyDtoList.add(historyDto);
        }
        return historyDtoList;
    }
}
