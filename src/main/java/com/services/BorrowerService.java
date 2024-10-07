package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.models.Book;
import com.models.Borrower;
import com.repositories.BookRepository;
import com.repositories.BorrowerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;
    
    @Autowired
    private BookRepository bookRepository;

    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }

    public Optional<Borrower> getBorrowerById(Long id) {
        return borrowerRepository.findById(id);
    }

    public List<Borrower> getBorrowersByBookId(Long bookId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            // Returns the list of borrowers for the book
            return book.getBorrowers().stream().toList(); 
        }
        
        throw new RuntimeException("Book with id " + bookId + " not found");
    }
    
    
    public Borrower createBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    public Borrower updateBorrower(Long id, Borrower updatedBorrower) {
        Optional<Borrower> existingBorrower = borrowerRepository.findById(id);
        if (existingBorrower.isPresent()) {
            Borrower borrower = existingBorrower.get();
            borrower.setName(updatedBorrower.getName());
            borrower.setEmail(updatedBorrower.getEmail());
            return borrowerRepository.save(borrower);
        } else {
            throw new RuntimeException("Borrower not found");
        }
    }

    public void deleteBorrower(Long id) {
        borrowerRepository.deleteById(id);
    }
}
