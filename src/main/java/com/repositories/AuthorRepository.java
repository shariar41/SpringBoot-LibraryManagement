package com.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	// Custom method to find an Author by name
    Optional<Author> findByName(String name);
}
