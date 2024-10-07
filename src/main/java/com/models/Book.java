package com.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "books")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String isbn;
    
    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date publishedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonManagedReference  
    private Author author;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "book_borrowers",
        joinColumns = {@JoinColumn(name = "book_id")},
        inverseJoinColumns = {@JoinColumn(name = "borrower_id")}
    )
//    @JsonManagedReference // Manages forward serialization
    private Set<Borrower> borrowers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id != null && id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + (author != null ? author.getName() : "null") +  // Avoid lazy loading issues
                ", publishedDate=" + publishedDate +
                ", isbn='" + isbn + '\'' +
                ", genre='" + genre + '\'' +
//                ", borrowers=" + borrowers.toString() + // Avoid printing all borrowers to prevent recursion
                '}';
    }
}