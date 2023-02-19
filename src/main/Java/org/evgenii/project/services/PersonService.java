package org.evgenii.project.services;

import org.evgenii.project.models.Book;
import org.evgenii.project.models.Person;
import org.evgenii.project.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return show(id).getBooks();
    }
}
