package org.evgenii.project.repositories;

import org.evgenii.project.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findFirstByNameStartingWith(String searchQuery);
}
