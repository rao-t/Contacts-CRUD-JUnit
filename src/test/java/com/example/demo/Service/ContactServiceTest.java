package com.example.demo.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.Entity.Contact;
import com.example.demo.Repository.ContactRepository;


@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @Test
    public void testAddContact() {
        Contact contact = new Contact();
        contact.setName("John Doe");
        contact.setPhone("1234567890");
        contact.setEmail("john@example.com");

        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        Contact savedContact = contactService.addContact(contact);

        assertEquals("John Doe", savedContact.getName());
        assertEquals("1234567890", savedContact.getPhone());
        assertEquals("john@example.com", savedContact.getEmail());
    }

    @Test
    public void testGetAllContacts() {
        Contact contact1 = new Contact();
        contact1.setId(1);
        contact1.setName("John Doe");
        contact1.setPhone("1234567890");
        contact1.setEmail("john@example.com");

        Contact contact2 = new Contact();
        contact2.setId(2);
        contact2.setName("Jane Doe");
        contact2.setPhone("0987654321");
        contact2.setEmail("jane@example.com");

        List<Contact> contacts = Arrays.asList(contact1, contact2);

        when(contactRepository.findAll()).thenReturn(contacts);

        List<Contact> retrievedContacts = contactService.getAllContacts();

        assertEquals(2, retrievedContacts.size());
        assertEquals("John Doe", retrievedContacts.get(0).getName());
        assertEquals("Jane Doe", retrievedContacts.get(1).getName());
    }

    @Test
    public void testGetContactById() {
        Contact contact = new Contact();
        contact.setId(1);
        contact.setName("John Doe");
        contact.setPhone("1234567890");
        contact.setEmail("john@example.com");

        when(contactRepository.findById(1)).thenReturn(Optional.of(contact));

        Optional<Contact> retrievedContact = contactService.getContactById(1);

        assertTrue(retrievedContact.isPresent());
        assertEquals("John Doe", retrievedContact.get().getName());
    }

    @Test
    public void testUpdateContact() {
        Contact existingContact = new Contact();
        existingContact.setId(1);
        existingContact.setName("John Doe");
        existingContact.setPhone("1234567890");
        existingContact.setEmail("john@example.com");

        Contact updatedContactDetails = new Contact();
        updatedContactDetails.setName("Jane Doe");
        updatedContactDetails.setPhone("0987654321");
        updatedContactDetails.setEmail("jane@example.com");

        when(contactRepository.findById(1)).thenReturn(Optional.of(existingContact));
        when(contactRepository.save(any(Contact.class))).thenReturn(updatedContactDetails);

        Contact updatedContact = contactService.updateContact(1, updatedContactDetails);

        assertEquals("Jane Doe", updatedContact.getName());
        assertEquals("0987654321", updatedContact.getPhone());
        assertEquals("jane@example.com", updatedContact.getEmail());
    }

    @Test
    public void testDeleteContact() {
        Contact contact = new Contact();
        contact.setId(1);
        contact.setName("John Doe");
        contact.setPhone("1234567890");
        contact.setEmail("john@example.com");

        when(contactRepository.existsById(1)).thenReturn(true);

        boolean isDeleted = contactService.deleteContact(1);

        assertTrue(isDeleted);
        verify(contactRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteContactNotFound() {
        when(contactRepository.existsById(1)).thenReturn(false);

        boolean isDeleted = contactService.deleteContact(1);

        assertFalse(isDeleted);
        verify(contactRepository, never()).deleteById(1);
    }
}
