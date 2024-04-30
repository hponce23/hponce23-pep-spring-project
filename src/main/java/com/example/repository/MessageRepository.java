package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query(value = "SELECT * FROM message WHERE posted_by=?", nativeQuery = true)
    List<Message> findMessagesByPostedId(int posted_by);

}