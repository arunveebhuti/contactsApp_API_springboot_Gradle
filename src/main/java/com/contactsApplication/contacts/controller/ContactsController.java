package com.contactsApplication.contacts.controller;

import com.contactsApplication.contacts.ResourceNotFoundException;
import com.contactsApplication.contacts.entity.Contact;
import com.contactsApplication.contacts.repository.ContactRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/addcontact")
    public Contact addContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }
    @GetMapping("/allcontacts")
    public ResponseEntity<List<Contact>> getAllContacts() {

        return ResponseEntity.ok(contactRepository.findAll());
    }
    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> geContactById(@PathVariable(value = "id") Integer contactId)
            throws ResourceNotFoundException {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found for this id :: " + contactId));
        return ResponseEntity.ok().body(contact);
    }
    
    @PutMapping("/contacts/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable(value = "id") Integer contactId,
                                                   @RequestBody Contact contactDetails) throws ResourceNotFoundException {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found for this id :: " + contactId));

        contact.setName(contactDetails.getName());
        contact.setEmail(contactDetails.getEmail());
        contact.setPlace(contact.getPlace());
        
        final Contact updatedContact = contactRepository.save(contact);
        return ResponseEntity.ok(updatedContact);
    }
    
    @DeleteMapping("/contact/{id}")
    public Map<String, Boolean> deleteContact(@PathVariable(value = "id") Integer contactId)
            throws ResourceNotFoundException {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found for this id :: " + contactId));

        contactRepository.delete(contact);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


}
