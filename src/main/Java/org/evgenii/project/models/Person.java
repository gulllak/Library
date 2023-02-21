package org.evgenii.project.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int person_id;
    @Column(name = "full_name")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 5, max = 50, message = "Имя должно быть не менее 5 и не более 50 символов")
    private String fullName;
    @Column(name = "birthdate")
    @Min(value = 1940, message = "Год рождения должен быть не менее чем 1940 г.")
    @Max(value = 2022, message = "Год рождения должен быть не более чем 2022 г.")
    private int birthdate;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(){
    }

    public Person(int person_id, String fullName, int birthdate) {
        this.person_id = person_id;
        this.fullName = fullName;
        this.birthdate = birthdate;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(int birthdate) {
        this.birthdate = birthdate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
