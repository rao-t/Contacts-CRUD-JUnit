package com.example.demo.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.Controller.ContactController;
import com.example.demo.Entity.Contact;
import com.example.demo.Service.ContactService;

@ExtendWith(MockitoExtension.class)
public class ContactControllerTest {

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    @Test
    public void testAddContact() {
        Contact contact = new Contact();
        contact.setName("John Doe");
        contact.setPhone("1234567890");
        contact.setEmail("john@example.com");

        when(contactService.addContact(any(Contact.class))).thenReturn(contact);

        Contact savedContact = contactController.addContact(contact);

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

        when(contactService.getAllContacts()).thenReturn(contacts);

        List<Contact> retrievedContacts = contactController.getAllContacts();

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

        when(contactService.getContactById(1)).thenReturn(Optional.of(contact));

        ResponseEntity<Object> responseEntity = contactController.getContactById(1);
        Contact retrievedContact = (Contact) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("John Doe", retrievedContact.getName());
    }

    @Test
    public void testGetContactByIdNotFound() {
        when(contactService.getContactById(1)).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = contactController.getContactById(1);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateContact() {
        Contact updatedContact = new Contact();
        updatedContact.setId(1);
        updatedContact.setName("Jane Doe");
        updatedContact.setPhone("0987654321");
        updatedContact.setEmail("jane@example.com");

        when(contactService.updateContact(1, updatedContact)).thenReturn(updatedContact);

        ResponseEntity<Contact> responseEntity = contactController.updateContact(1, updatedContact);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Jane Doe", responseEntity.getBody().getName());
    }

    @Test
    public void testUpdateContactNotFound() {
        Contact updatedContact = new Contact();
        updatedContact.setId(1);
        updatedContact.setName("Jane Doe");
        updatedContact.setPhone("0987654321");
        updatedContact.setEmail("jane@example.com");

        when(contactService.updateContact(1, updatedContact)).thenThrow(new RuntimeException("Contact not found"));

        ResponseEntity<Contact> responseEntity = contactController.updateContact(1, updatedContact);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteContact() {
        when(contactService.deleteContact(1)).thenReturn(true);

        ResponseEntity<String> responseEntity = contactController.deleteContact(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("deleted successfully"));
    }

    @Test
    public void testDeleteContactNotFound() {
        when(contactService.deleteContact(1)).thenReturn(false);

        ResponseEntity<String> responseEntity = contactController.deleteContact(1);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}

