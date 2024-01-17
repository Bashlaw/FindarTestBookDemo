package com.findar.demo.dto;

import com.findar.demo.entity.Book;
import com.findar.demo.entity.BookCopyView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DTOFactoryTest {

    @Test
    void when_BookEntityHasNoCopies_Expect_CorrectBookDTO_WithNoCopyDTOs() {
        Book book =
                new Book(
                        1,
                        "Title",
                        "Author",
                        "ISBN");

        BookDTO expectedBookDTO =
                new BookDTO(
                        1,
                        "Title",
                        "Author",
                        "ISBN",
                        0,
                        null);

        BookDTO result = new DTO_Factory().createDTO(book);

        assertEquals(expectedBookDTO, result);
    }

    @Test
    void when_BookEntityHasOneCopy_Expect_CorrectBookDTO_WithOneCopyDTO() {
        BookCopyView book =
                new BookCopyView(
                        1,
                        "Title",
                        "Author",
                        "ISBN",
                        1);

        BookDTO expectedBookDTO =
                new BookDTO(
                        1,
                        "Title",
                        "Author",
                        "ISBN",
                        1,
                        null);

        BookDTO result = new DTO_Factory().createDTO(book);

        assertEquals(expectedBookDTO, result);
    }


}
