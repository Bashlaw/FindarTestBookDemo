package com.findar.demo.book;

import com.findar.demo.dto.BookDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookRestControllerTest {

    @Test
    void when_NoBooks_expect_EmptyListOfBookDTO() {
        BookService mockBookService = mock(BookService.class);
        when(mockBookService.getSummaryOfAllBooks()).thenReturn(new ArrayList<>());

        BookRestController bookRestController = new BookRestController(mockBookService);
        List<BookDTO> result = bookRestController.getSummaryOfAllBooks();

        assertEquals(0, result.size());
    }

    @Test
    void when_ThreeBooks_expect_GetSummaryOfAllBooksReturnsListFromBookService() {
        List<BookDTO> bookDTOList =
                Arrays.asList(
                        mock(BookDTO.class),
                        mock(BookDTO.class),
                        mock(BookDTO.class)
                );

        BookService mockBookService = mock(BookService.class);
        when(mockBookService.getSummaryOfAllBooks()).thenReturn(bookDTOList);

        BookRestController bookRestController = new BookRestController(mockBookService);
        List<BookDTO> result = bookRestController.getSummaryOfAllBooks();

        assertEquals(bookDTOList, result);
    }

    @Test
    void when_GetSummaryOfAllBooks_expect_OneCallToBookService_GetSummaryOfAllBooks() {
        BookService mockBookService = mock(BookService.class);
        BookRestController bookRestController = new BookRestController(mockBookService);

        bookRestController.getSummaryOfAllBooks();

        verify(mockBookService, times(1)).getSummaryOfAllBooks();
        verifyNoMoreInteractions(mockBookService);
    }

    @Test
    void when_BookExists_expect_NonNullBookDTO() {
        BookService mockBookService = mock(BookService.class);
        when(mockBookService.getBookDetails(anyInt())).thenReturn(mock(BookDTO.class));

        BookRestController bookRestController = new BookRestController(mockBookService);
        BookDTO result = bookRestController.getBookDetails(anyInt());

        assertNotNull(result);
    }

    @Test
    void when_BookDoesNotExist_expect_Null() {
        BookService mockBookService = mock(BookService.class);
        when(mockBookService.getBookDetails(anyInt())).thenReturn(null);

        BookRestController bookRestController = new BookRestController(mockBookService);
        BookDTO result = bookRestController.getBookDetails(anyInt());

        assertNull(result);
    }

}