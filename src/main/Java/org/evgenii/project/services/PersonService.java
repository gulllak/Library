package org.evgenii.project.services;

import org.evgenii.project.models.Book;
import org.evgenii.project.models.Person;
import org.evgenii.project.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> index() {
        return personRepository.findAll();
    }

    public Person show(int id) {
        return personRepository.findById(id).orElse(null);
    }

    public Optional<Person> show(String fullName) {
        return personRepository.findByFullName(fullName);
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Person updatePerson) {
        Person person = show(id);
        person.setFullName(updatePerson.getFullName());
        person.setBirthdate(updatePerson.getBirthdate());

        personRepository.save(person);
    }

    public List<Book> usersBook(int id) {
        Optional<Person> person = personRepository.findById(id);

        if(person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            checkTimer(person.get().getBooks());
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }

    private void checkTimer(List<Book> books) {
        for (Book book : books) {
            if(book.getBookTimer() != null) {
                boolean bool = (LocalDateTime.now().getDayOfMonth() - book.getBookTimer().getDayOfMonth()) > 10;
                book.setExpired(bool);
            }
        }
    }
}
