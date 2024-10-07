package com.services;
import org.springframework.stereotype.Service;

import com.models.Author;
import com.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Get all authors
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Get an author by ID
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    // Add a new author
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    // Update an existing author
    public Optional<Author> updateAuthor(Long id, Author authorDetails) {
        return authorRepository.findById(id).map(author -> {
            author.setName(authorDetails.getName());
            return authorRepository.save(author);
        });
    }

    // Delete an author
    public boolean deleteAuthor(Long id) {
        return authorRepository.findById(id).map(author -> {
            authorRepository.delete(author);
            return true;
        }).orElse(false);
    }
}
