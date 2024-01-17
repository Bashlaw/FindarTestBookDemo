package com.findar.demo.dto;

import com.findar.demo.entity.Book;
import com.findar.demo.entity.BookCopyView;
import com.findar.demo.entity.Copy;
import org.springframework.stereotype.Component;

@Component
public class DTO_Factory {

    public BookDTO createDTO(Book b) {

        return new BookDTO(
                b.getId(),
                b.getTitle(),
                b.getAuthor(),
                b.getIsbn());
    }

    public BookDTO createDTO(BookCopyView b) {
        BookDTO bookDTO =
                new BookDTO(
                        b.getId(),
                        b.getTitle(),
                        b.getAuthor(),
                        b.getIsbn());
        bookDTO.setNumberOfCopies(b.getNumberOfCopies());

        return bookDTO;
    }

    public CopyDTO createDTO(Copy copy, BookDTO book, String status) {
        return new CopyDTO(copy.getId(), book, status);
    }

}
