package com.example.demo.controller;

import com.example.demo.dto.Message;
import com.example.demo.repository.PersonRepository;
import com.example.demo.dto.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
public class PersonController {
    @Autowired
    private PersonRepository repository;
    private final PersonService service;
    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping("/person")
    public Iterable<Person> getPersons() {
        return service.getPersonsService();
    }

    @GetMapping("/person/{id}")
    public Optional<Person> findPersonById(@PathVariable int id) {
        return service.findPersonByIdService(id);
    }

    @GetMapping("/person/{p_id}/message")
    public List<Message> getMessagesPerson(@PathVariable int p_id) {
        return service.getMessagesPersonService(p_id);
    }
    @GetMapping("/person/{p_id}/message/{m_id}")
    public Message findMessagePerson(@PathVariable int p_id, @PathVariable int m_id ) {
        return service.findMessagePersonService(p_id,m_id);
    }
    @PostMapping("/person")
    public Person addPerson(@RequestBody Person person) {
        return service.addPersonService(person);
    }
    @PostMapping("/person/{id}/message")
    public Object addMessage(@PathVariable int id, @RequestBody Message message) {
        return service.addMeesageService(id,message);
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {
        return service.updatePersonService(id,person);
    }
    @DeleteMapping("/person/{id}")
    public void deletePerson(@PathVariable int id) {
        service.deletePersonService(id);
    }
    @DeleteMapping("/person/{p_id}/message/{m_id}")
    public void deleteMessagePerson(@PathVariable int p_id, @PathVariable int m_id) {
        service.deleteMessagePersonService(p_id,m_id);
    }
}
