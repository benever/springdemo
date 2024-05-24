package com.example.demo.service;

import com.example.demo.dto.Message;
import com.example.demo.dto.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Iterable<Person> getPersonsService() {
        return repository.findAll();
    }
    public Optional<Person> findPersonByIdService(int id) {
        return repository.findById(id);
    }
    public List<Message> getMessagesPersonService(int id) {
        return repository.findById(id).get().getMessages();
    }
    public Message findMessagePersonService(int p_id, int m_id ) {
        int id = 0;
        for (int i = 0; i < repository.findById(p_id).get().getMessages().size(); i++) {
            if(m_id == repository.findById(p_id).get().getMessages().get(i).getId()){
                id = i;
            }
        }
        return repository.findById(p_id).get().getMessages().get(id);
    }
    public Person addPersonService(Person person) {
        repository.save(person);
        return person;
    }
    public Object addMeesageService(int personId, Message message) {
        if(repository.existsById(personId)){
            Person person = repository.findById(personId).get();
            message.setPerson(person);
            message.setTime(LocalDateTime.now());
            person.addMessage(message);
            return repository.save(person);
        } else{
            return HttpStatus.BAD_REQUEST;
        }
    }
    public ResponseEntity<Person> updatePersonService(int id, Person person) {
        Person newPerson = repository.findById(id).orElse(new Person());
        newPerson.setFirstname(person.getFirstname());
        newPerson.setSurname(person.getSurname());
        newPerson.setLastname(person.getLastname());
        newPerson.setBirthday(person.getBirthday());
        HttpStatus status = repository.existsById(id) ? HttpStatus.OK : HttpStatus.CREATED;
        return new ResponseEntity(repository.save(newPerson), status);
    }
    public void deletePersonService( int id) {
        repository.deleteById(id);
    }
    public void deleteMessagePersonService(int p_id, int m_id) {
        int id = 0;
        for (int i = 0; i < repository.findById(p_id).get().getMessages().size(); i++) {
            if(m_id == repository.findById(p_id).get().getMessages().get(i).getId()){
                id = i;
            }
        }

        Person newPerson = repository.findById(p_id).orElse(new Person());
        repository.findById(p_id).get().getMessages().remove(id);
        newPerson.setFirstname(repository.findById(p_id).get().getFirstname());
        newPerson.setSurname(repository.findById(p_id).get().getSurname());
        newPerson.setLastname(repository.findById(p_id).get().getLastname());
        newPerson.setBirthday(repository.findById(p_id).get().getBirthday());
        newPerson.setMessages(repository.findById(p_id).get().getMessages());

        repository.save(newPerson);
    }
}