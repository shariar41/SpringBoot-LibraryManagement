package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.models.Author;
import com.services.AuthorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author createdAuthor = authorService.addAuthor(author);
        return ResponseEntity.ok(createdAuthor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Author>> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = authorService.getAuthorById(id);
        return ResponseEntity.ok(author);
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Author>> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        Optional<Author> updatedAuthor = authorService.updateAuthor(id, author);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
