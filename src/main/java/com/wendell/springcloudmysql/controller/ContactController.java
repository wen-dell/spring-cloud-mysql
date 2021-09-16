package com.wendell.springcloudmysql.controller;

import com.wendell.springcloudmysql.model.Contact;
import com.wendell.springcloudmysql.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private ContactRepository contactRepository;

    ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> findById(@PathVariable Long id) {
        return contactRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Contact create(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> update(@PathVariable("id") Long id, @RequestBody Contact contact) {
        return contactRepository.findById(id)
                .map(record -> {
                    record.setName(contact.getName());
                    record.setEmail(contact.getEmail());
                    record.setPhone(contact.getPhone());
                    Contact updated = contactRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return contactRepository.findById(id)
                .map(record -> {
                    contactRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
