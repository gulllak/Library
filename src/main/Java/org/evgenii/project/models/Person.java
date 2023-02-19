package org.evgenii.project.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int person_id;
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 5, max = 50, message = "Имя должно быть не менее 5 и не более 50 символов")
    private String full_name;
    @Min(value = 1940, message = "Год рождения должен быть не менее чем 1940 г.")
    @Max(value = 2022, message = "Год рождения должен быть не более чем 2022 г.")
    private int birthdate;

    public Person(){
    }

    public Person(int person_id, String full_name, int birthdate) {
        this.person_id = person_id;
        this.full_name = full_name;
        this.birthdate = birthdate;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(int birthdate) {
        this.birthdate = birthdate;
    }
}
