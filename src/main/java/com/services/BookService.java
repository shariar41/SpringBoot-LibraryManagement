package com.services;

import com.dtos.BookDTO;
import com.dtos.BorrowerDTO;
import com.models.Author;
import com.models.Book;
import com.models.Borrower;
import com.repositories.AuthorRepository;
import com.repositories.BookRepository;
import com.repositories.BorrowerRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BorrowerRepository borrowerRepository;

	// Create a new book
	public Book createBook(BookDTO bookDTO) {
		Book book = new Book();
		book.setTitle(bookDTO.getTitle());
		book.setIsbn(bookDTO.getIsbn());
		book.setGenre(bookDTO.getGenre());
		book.setPublishedDate(bookDTO.getPublishedDate());
		 // Set author
        Optional<Author> author = authorRepository.findById(bookDTO.getAuthor());
        author.ifPresent(book::setAuthor);

     // Set borrowers if present
        if (bookDTO.getBorrowers() != null) {
            Set<Borrower> borrowers = new HashSet<>();
            for (BorrowerDTO borrowerDto : bookDTO.getBorrowers()) {
                Optional<Borrower> borrower = borrowerRepository.findById(borrowerDto.getId());
                borrower.ifPresent(borrowers::add);
            }
            book.setBorrowers(borrowers);
        }
		return bookRepository.save(book);
	}

	// Get all books
	public List<Book> getAllBooks() {
	    List<Book> books = bookRepository.findAll(); // Fetch all books from the repository

	    // Map each Book to a BookDTO
	    return books;
	    }


	// Get a book by ID
	public Book getBookById(Long id) {
		Optional<Book> book = bookRepository.findById(id);
		return book.orElseThrow(() -> new RuntimeException("Book not found"));
	}

	public List<BookDTO> findBooksByBorrowerId(Long borrowerId) {
	    List<Book> books = bookRepository.findByBorrowersId(borrowerId); // Adjust based on your repository
	    return books.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	// Update a book
	public Book updateBook(Long id, Book updatedBook) {
		if (!bookRepository.existsById(id)) {
			throw new RuntimeException("Book not found");
		}
		updatedBook.setId(id); // Make sure to set the ID
		return bookRepository.save(updatedBook);
	}

	// Delete a book
	public void deleteBook(Long id) {
		if (!bookRepository.existsById(id)) {
			throw new RuntimeException("Book not found");
		}
		bookRepository.deleteById(id);
	}
	public BookDTO addBorrowerToBook(Long bookId, Long borrowerId) {
//        log.info("Book ID: " + bookId + " | Borrower ID: " + borrowerId);
        System.out.print("Book ID: ");
        System.out.println("Book ID: "+ bookId +" kaskdjaksdjan");
        System.out.println("Book ID: "+ borrowerId +" kaskdjaksdjan");
//        log.info("Adding Borrower ID: {} to Book ID: {}", borrowerId, bookId);

        // Find the book and borrower by their IDs
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        Optional<Borrower> borrowerOpt = borrowerRepository.findById(borrowerId);

        if (bookOpt.isPresent() && borrowerOpt.isPresent()) {
        	System.out.println("all present!!!");
            Book book = bookOpt.get();
            Borrower borrower = borrowerOpt.get();
            System.out.println("step 2 done");
            // Add borrower to the book and book to the borrower
            book.getBorrowers().add(borrower);
            borrower.getBooks().add(book);
            System.out.println("step 3 done");
            System.out.println(book.toString());

            System.out.println("step 3 2 done");
            // Save the updated book, this will also update the borrower's list due to cascading
            try {
                bookRepository.save(book);
            } catch (Exception e) {
                e.printStackTrace(); // This will print the full stack trace in the console.
                log.error("Error saving book: " + e.getMessage()); // Log the exception message.
            }

            System.out.println("step 4 done");
            System.out.println(book);
            // Convert and return as DTO
            System.out.println(convertToDto(book));
            return convertToDto(book);
        } else {
            throw new RuntimeException("Book or Borrower not found"); // Handle entity not found
        }
    }

    public BookDTO removeBorrowerFromBook(Long bookId, Long borrowerId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        Optional<Borrower> borrowerOpt = borrowerRepository.findById(borrowerId);

        if (bookOpt.isPresent() && borrowerOpt.isPresent()) {
            Book book = bookOpt.get();
            Borrower borrower = borrowerOpt.get();

            book.getBorrowers().remove(borrower);
            borrower.getBooks().remove(book);

            bookRepository.save(book); // Saves both sides of the relationship
            return convertToDto(book);
        }
        return null; // Handle appropriately
    }
    private BookDTO convertToDto(Book book) {
        // Convert the book entity to BookDTO
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());

        // Handle the case where the author might be null
        if (book.getAuthor() != null) {
            dto.setAuthor(book.getAuthor().getId());  // Assuming `BookDTO` has `authorId`
        }

        dto.setPublishedDate(book.getPublishedDate());
        dto.setIsbn(book.getIsbn());
        dto.setGenre(book.getGenre());

        // Map the borrowers to BorrowerDTO
        dto.setBorrowers(
                book.getBorrowers().stream()
                        .map(borrower -> new BorrowerDTO(
                                borrower.getId(),
                                borrower.getName(),
                                borrower.getEmail()
                        ))
                        .collect(Collectors.toList())
        );
        return dto;
    }

}
