package com.example.wistron.db.repository;

import com.example.wistron.service.model.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findContactByNumber(String number);
}
