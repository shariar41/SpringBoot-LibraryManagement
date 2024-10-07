package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	@Query("SELECT b FROM Book b JOIN b.borrowers br WHERE br.id = :borrowerId")
	List<Book> findByBorrowersId(@Param("borrowerId") Long borrowerId);

}
