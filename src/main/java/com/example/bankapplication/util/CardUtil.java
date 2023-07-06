package com.example.bankapplication.util;

import com.example.bankapplication.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CardUtil {
    public static String generateCardNum () {
        String num = "";
        for (int i = 0; i < 16; i++) {
            num = num + String.valueOf((int)(Math.random()*10));
        }
        num = num.replaceAll("\\d{4}", "$0 ");

        return num.substring(0,num.length()-1);
    }

    public static String cardDate () {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,3);
        Date date = calendar.getTime();
        String finDate = new SimpleDateFormat("MM/yy").format(date);
        System.out.println(finDate);
        return finDate;
    }

}
