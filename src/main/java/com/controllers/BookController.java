package com.controllers;

import com.dtos.BookDTO;
import com.models.Book;
import com.repositories.AuthorRepository;
import com.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    @Autowired
    private BookService bookService;
    
    @Autowired
    private AuthorRepository authorRepository;

    // Create a new book
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO) {
        Book createdBook = bookService.createBook(bookDTO);
//        BookDTO createdBookDTO = convertToDTO(createdBook);
        return ResponseEntity.ok(createdBook);
    }

    // Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }
    // Get a book by book ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        BookDTO bookDTO = convertToDTO(book);
        return ResponseEntity.ok(bookDTO);
    }
 // Get a book by Borrower ID
    @GetMapping("borrowers/")
    public ResponseEntity<List<BookDTO>> getBooksByBorrowerId(@RequestParam Long borrowerId) {
        List<BookDTO> books = bookService.findBooksByBorrowerId(borrowerId);
        return ResponseEntity.ok(books);
    }

    // Update a book
//    @PutMapping("/{id}")
//    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
//        Book book = bookService.getBookById(id);
//        book.setTitle(bookDTO.getTitle());
//        book.setIsbn(bookDTO.getIsbn());
//        book.setGenre(bookDTO.getGenre());
//        book.setAuthor(authorRepository.findById(bookDTO.getId()));
//        book.setPublishedDate(bookDTO.getPublishedDate());
//        Book updatedBook = bookService.updateBook(id, book);
//        BookDTO updatedBookDTO = convertToDTO(updatedBook);
//        return ResponseEntity.ok(updatedBookDTO);
//    }

    // Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{bookId}/borrowers/{borrowerId}")
    public ResponseEntity<BookDTO> addBorrowerToBook(@PathVariable Long bookId, @PathVariable Long borrowerId) {
    	BookDTO updatedBook = bookService.addBorrowerToBook(bookId, borrowerId);
    	System.out.println("Updated Book: " + updatedBook); // Log the data here
        
        return ResponseEntity.ok(updatedBook);
    }

    
    @DeleteMapping("/{bookId}/borrowers/{borrowerId}")
    public ResponseEntity<BookDTO> removeBorrowerFromBook(@PathVariable Long bookId, @PathVariable Long borrowerId) {
    	BookDTO updatedBook = bookService.removeBorrowerFromBook(bookId, borrowerId);
        return ResponseEntity.ok(updatedBook);
    }

    // Helper method to convert Book to BookDTO
    private BookDTO convertToDTO(Book book) {
    	BookDTO dto = new BookDTO();
    	dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor().getId());
        dto.setPublishedDate(book.getPublishedDate());
        dto.setIsbn(book.getIsbn());
        
        // Optionally convert borrowers to DTOs
        return dto;
    }
}
