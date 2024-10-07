package com.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    private Long id;
    private String name;
    private List<BookDTO> books;

    // Getters and Setters...
}
