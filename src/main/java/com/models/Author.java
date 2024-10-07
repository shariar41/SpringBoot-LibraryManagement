package com.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Data
@Entity
//@Table(name = "authors")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonBackReference 
    @JsonIgnore
    private Set<Book> books = new HashSet<>();
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author ) o;
        return id != null && id.equals(author.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                // Don't include books in toString to avoid recursion
                '}';
    }
}
