package org.evgenii.project.controllers;

import org.evgenii.project.services.PersonService;
import org.evgenii.project.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.evgenii.project.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonService personService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personService.index());

        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int person_id, Model model) {
        model.addAttribute("person", personService.show(person_id));
        model.addAttribute("books", personService.usersBook(person_id));

        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person")Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()) {
            return "people/new";
        }
        personService.save(person);

        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int person_id,
                       Model model) {
        model.addAttribute("person", personService.show(person_id));

        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int person_id) {
        person.setPerson_id(person_id);
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        personService.update(person_id, person);

        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int person_id){
        personService.delete(person_id);
        return "redirect:/people";
    }
}
