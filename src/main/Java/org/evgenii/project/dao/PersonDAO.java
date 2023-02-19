//package org.evgenii.project.dao;
//
//import org.evgenii.project.models.Person;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class PersonDAO {
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public PersonDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Person> index() {
//        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
//    }
//
//    public Person show(int person_id) {
//        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id = ?", new BeanPropertyRowMapper<>(Person.class), person_id)
//                .stream().findAny().orElse(null);
//    }
//
//    public Optional<Person> show(String full_name) {
//        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name = ?", new BeanPropertyRowMapper<>(Person.class), full_name)
//                .stream().findAny();
//    }
//
//    public void save(Person person)  {
//        jdbcTemplate.update("INSERT INTO Person(full_name, birthdate) VALUES (?,?)", person.getFull_name(), person.getBirthdate());
//    }
//
//    public void update(int person_id, Person updatePerson)  {
//        jdbcTemplate.update("UPDATE Person SET full_name =  ?, birthdate =  ? WHERE person_id = ?", updatePerson.getFull_name(), updatePerson.getBirthdate(), person_id);
//    }
//
//    public void delete(int person_id) {
//        jdbcTemplate.update("DELETE FROM Person WHERE person_id = ?", person_id);
//    }
//}
