package com.example.wistron.service.service;

import com.example.wistron.service.model.dto.ContactDTO;
import com.example.wistron.service.model.entity.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    void registration(String name, String surname, String number) throws Exception;

    Optional<Contact> getContact(Long id) throws Exception;

    List<ContactDTO> getAllContacts() throws Exception;

    void delete(Long id);

    void update(ContactDTO contactDTO, Long id);
}
