package com.example.wistron.service.service;

import com.example.wistron.db.repository.ContactRepository;
import com.example.wistron.service.exception.ExistingContactException;
import com.example.wistron.service.model.dto.ContactDTO;
import com.example.wistron.service.model.entity.Contact;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Override
    public void registration(String name, String surname, String number) throws Exception {
        if (contactRepository.findContactByNumber(number) != null) {
            log.error("Status: number already registered");
            throw new ExistingContactException("The number is already registered!", LocalDateTime.now());

        } else {
            Contact contact = new Contact();
            contact.setName(name);
            contact.setSurname(surname);
            contact.setNumber(number);
            contactRepository.save(contact);
        }
    }

    @Override
    public Optional<Contact> getContact(Long id) {
        if (id != null) {
            return contactRepository.findById(id);
        } else {
            log.error("Status: contact with null id cannot be found");
            throw new IllegalArgumentException("ID cannot be null !");
        }
    }

    @Override
    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(contact -> {
                    ContactDTO contactDTO = new ContactDTO();
                    contactDTO.setName(contact.getName());
                    contactDTO.setSurname(contact.getSurname());
                    contactDTO.setNumber(contact.getNumber());
                    return contactDTO;
                })
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (id == null || !this.contactRepository.existsById(id)) {
            log.error("Status: contact with such id doesn't exist");
            throw new IllegalArgumentException("No contact with this ID found!");
        }

        this.contactRepository.deleteById(id);
    }

    @Override
    public void update(ContactDTO contactDTO, Long id) {
        Optional<Contact> contact = (contactRepository.findById(id));

        contact.ifPresent(c -> {
            c.setName(contactDTO.getName());
            c.setSurname(contactDTO.getSurname());
            c.setNumber(contactDTO.getNumber());
            contactRepository.save(c);
        });

        if (contact.isEmpty()) {
            log.error("Status: contact with such id doesn't exist");
            throw new IllegalArgumentException("No contact with this ID found!");
        }
    }
}
