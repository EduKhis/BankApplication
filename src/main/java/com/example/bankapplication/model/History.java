package com.example.bankapplication.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
@Data
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne
    private CreditCard cardSender;
    @OneToOne
    private CreditCard cardReceiver;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private BigDecimal sum;
    private Date date;

    public String getDateForOutput(){
        return new SimpleDateFormat("dd.MM.yyyy").format(date); // 2011-01-18
    }

}
