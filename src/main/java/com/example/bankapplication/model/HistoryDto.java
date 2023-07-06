package com.example.bankapplication.model;

import lombok.Data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class HistoryDto {
    private Long id;
    private String numberSender;
    private String numberReceiver;
    private Currency currency;
    private BigDecimal sum;
    private Date date;

    public HistoryDto(Long id, String numberSender, String numberReceiver, Currency currency, BigDecimal sum, Date date) {
        this.id = id;
        this.numberSender = numberSender;
        this.numberReceiver = numberReceiver;
        this.currency = currency;
        this.sum = sum;
        this.date = date;
    }

    public HistoryDto(String numberSender, String numberReceiver, Currency currency, BigDecimal sum, Date date) {
        this.numberSender = numberSender;
        this.numberReceiver = numberReceiver;
        this.currency = currency;
        this.sum = sum;
        this.date = date;
    }
    public String getDateForOutput(){
        return new SimpleDateFormat("dd.MM.yyyy").format(date); // 2011-01-18
    }
}
