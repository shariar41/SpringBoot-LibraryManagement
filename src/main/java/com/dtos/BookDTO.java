package com.dtos;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

//    public BookDTO(Long id2, String title2, String isbn2, String genre2, Date publishedDate2, Object author2,
//			List<BorrowerDTO> borrowerDTOs) {
//		// TODO Auto-generated constructor stub
//	}
	private Long id;
    private String title;
    private String isbn;
    private String genre;
    private Date publishedDate;
//    private AuthorDTO author;
    private Long author; // Just the author ID for creation
    private List<BorrowerDTO> borrowers;


    // Getters and Setters...
}
