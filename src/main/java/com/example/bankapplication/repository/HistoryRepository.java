package com.example.bankapplication.repository;

import com.example.bankapplication.model.CreditCard;
import com.example.bankapplication.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History,Long> {

@Query(value = "SELECT * FROM bank.history AS h WHERE h.card_receiver_id=?1 or h.card_sender_id=?2", nativeQuery = true)

    List<History> findByCardReceiverOrCardSender(Long idR, Long idS);

}
