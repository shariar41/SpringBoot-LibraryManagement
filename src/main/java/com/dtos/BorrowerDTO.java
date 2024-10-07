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
public class BorrowerDTO {

    private Long id;
    private String name;
    private String email;
//    private List<BookDTO> borrowedBooks;
}
