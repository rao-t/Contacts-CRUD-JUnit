package com.example.demo.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Contact;
import com.example.demo.Repository.ContactRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact addContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getContactById(int id) {
        return contactRepository.findById(id);
    }

    public Contact updateContact(int id, Contact contactDetails) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
        contact.setName(contactDetails.getName());
        contact.setPhone(contactDetails.getPhone());
        contact.setEmail(contactDetails.getEmail());
        return contactRepository.save(contact);
    }

    public boolean deleteContact(int id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

