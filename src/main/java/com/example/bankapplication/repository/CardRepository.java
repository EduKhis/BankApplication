package com.example.bankapplication.repository;

import com.example.bankapplication.model.CreditCard;
import com.example.bankapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<CreditCard,Long> {
    List<CreditCard> findByUser (User user);
    boolean existsByNumber(String number);
    CreditCard findByNumber (String number);

}
