package com.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.models.Borrower;
import com.services.BookService;
import com.services.BorrowerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrowers")
@CrossOrigin(origins = "http://localhost:4200")
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;
    

    @GetMapping
    public List<Borrower> getAllBorrowers() {
        return borrowerService.getAllBorrowers();
    }
    
    @GetMapping("/returners")
    public ResponseEntity<List<Borrower>> getBorrowersByBookId(@RequestParam("bookId") Long bookId) {
        List<Borrower> borrowers = borrowerService.getBorrowersByBookId(bookId);
        return ResponseEntity.ok(borrowers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Borrower> getBorrowerById(@PathVariable Long id) {
        Optional<Borrower> borrower = borrowerService.getBorrowerById(id);
        return borrower.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Borrower> createBorrower(@RequestBody Borrower borrower) {
        Borrower newBorrower = borrowerService.createBorrower(borrower);
//        return ResponseEntity.ok(newBorrower);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBorrower);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Borrower> updateBorrower(@PathVariable Long id, @RequestBody Borrower borrower) {
        Borrower updatedBorrower = borrowerService.updateBorrower(id, borrower);
        return ResponseEntity.ok(updatedBorrower);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrower(@PathVariable Long id) {
        borrowerService.deleteBorrower(id);
        return ResponseEntity.noContent().build();
    }
}
