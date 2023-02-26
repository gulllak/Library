package org.evgenii.project.util;

import org.evgenii.project.models.Person;
import org.evgenii.project.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Person checkingPerson = personService.show(person.getFullName()).orElse(null);

        if(checkingPerson != null && person.getPerson_id() != checkingPerson.getPerson_id()) {
            errors.rejectValue("fullName", "", "Такое ФИО уже есть");
        }
    }
}
