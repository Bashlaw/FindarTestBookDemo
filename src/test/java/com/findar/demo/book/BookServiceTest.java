package com.findar.demo.book;

import com.findar.demo.copy.CopyService;
import com.findar.demo.dto.BookDTO;
import com.findar.demo.dto.DTO_Factory;
import com.findar.demo.entity.Book;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    @Test
    void when_ZeroBookEntities_Expect_GetSummaryOfAllBooksMakesOneCallToBookRepositoryFindAllAndNoInteractionWithDtoFactory() {
        List<Book> bookList = Collections.EMPTY_LIST;

        BookRepository mockBookRepository = mock(BookRepository.class);
        when(mockBookRepository.findAll()).thenReturn(bookList);

        DTO_Factory mockDto_factory = mock(DTO_Factory.class);
        CopyService mockCopyService = mock(CopyService.class);
        BookCopyRepository mockBookCopyRepository = mock(BookCopyRepository.class);
        BookService bookService = new BookService(mockDto_factory, mockCopyService, mockBookRepository, mockBookCopyRepository);

        bookService.getSummaryOfAllBooks();

        verify(mockBookRepository, times(1)).findAll();
        verifyNoInteractions(mockDto_factory);
    }

    @Test
    void when_ZeroBookEntities_Expect_GetSummaryOfAllBooksReturnsEmptyBookDtoList() {
        List<Book> bookList = Collections.EMPTY_LIST;

        BookRepository mockBookRepository = mock(BookRepository.class);
        when(mockBookRepository.findAll()).thenReturn(bookList);

        DTO_Factory mockDto_factory = mock(DTO_Factory.class);
        when(mockDto_factory.createDTO(any(Book.class)))
                .thenReturn(mock(BookDTO.class));


        CopyService mockCopyService = mock(CopyService.class);
        BookCopyRepository mockBookCopyRepository = mock(BookCopyRepository.class);

        BookService bookService = new BookService(mockDto_factory, mockCopyService, mockBookRepository, mockBookCopyRepository);

        List<BookDTO> result = bookService.getSummaryOfAllBooks();

        assertTrue(result.isEmpty());
    }

    @Test
    void when_ThreeBookEntities_Expect_GetSummaryOfAllBooksReturnsListOfThreeBookDTOs() {
        List<Book> bookList =
                Arrays.asList(
                        mock(Book.class),
                        mock(Book.class),
                        mock(Book.class)
                );

        BookRepository mockBookRepository = mock(BookRepository.class);
        when(mockBookRepository.findAll()).thenReturn(bookList);

        DTO_Factory mockDto_factory = mock(DTO_Factory.class);
        when(mockDto_factory.createDTO(any(Book.class)))
                .thenReturn(mock(BookDTO.class));

        CopyService mockCopyService = mock(CopyService.class);
        BookCopyRepository mockBookCopyRepository = mock(BookCopyRepository.class);

        BookService bookService = new BookService(mockDto_factory, mockCopyService, mockBookRepository, mockBookCopyRepository);


        List<BookDTO> result = bookService.getSummaryOfAllBooks();

        assertEquals(3, result.size());
    }

    @Test
    void when_BookIdExists_Expect_GetBookDetailsMakesOneCallToBookRepositoryFindByIdAndOneCallsToDtoFactoryCreateDTO() {
        BookRepository mockBookRepository = mock(BookRepository.class);
        when(mockBookRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Book.class)));

        DTO_Factory mockDto_factory = mock(DTO_Factory.class);
        CopyService mockCopyService = mock(CopyService.class);
        BookCopyRepository mockBookCopyRepository = mock(BookCopyRepository.class);

        BookService bookService = new BookService(mockDto_factory, mockCopyService, mockBookRepository, mockBookCopyRepository);


        bookService.getBookDetails(anyInt());

        verify(mockBookRepository, times(1)).findById(anyInt());
        verify(mockDto_factory, times(1)).createDTO(any(Book.class));
    }

    @Test
    void when_BookIdExists_Expect_GetBookDetailsReturnsNonNullBookDTO() {
        BookRepository mockBookRepository = mock(BookRepository.class);
        when(mockBookRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Book.class)));

        DTO_Factory mockDto_factory = mock(DTO_Factory.class);
        when(mockDto_factory.createDTO(any(Book.class)))
                .thenReturn(mock(BookDTO.class));

        CopyService mockCopyService = mock(CopyService.class);
        BookCopyRepository mockBookCopyRepository = mock(BookCopyRepository.class);

        BookService bookService = new BookService(mockDto_factory, mockCopyService, mockBookRepository, mockBookCopyRepository);


        BookDTO result = bookService.getBookDetails(anyInt());

        assertNotNull(result);
    }

    @Test
    void when_BookIdDoesNotExist_Expect_GetBookDetailsMakesOneCallToBookRepositoryFindByIdAndNoCallsToDtoFactoryCreateDTO() {
        BookRepository mockBookRepository = mock(BookRepository.class);
        when(mockBookRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        DTO_Factory mockDto_factory = mock(DTO_Factory.class);
        CopyService mockCopyService = mock(CopyService.class);
        BookCopyRepository mockBookCopyRepository = mock(BookCopyRepository.class);

        BookService bookService = new BookService(mockDto_factory, mockCopyService, mockBookRepository, mockBookCopyRepository);


        bookService.getBookDetails(anyInt());

        verify(mockBookRepository, times(1)).findById(anyInt());
        verifyNoInteractions(mockDto_factory);
    }

    @Test
    void when_BookIdDoesNotExist_Expect_GetBookDetailsReturnsNull() {
        BookRepository mockBookRepository = mock(BookRepository.class);
        when(mockBookRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        DTO_Factory mockDto_factory = mock(DTO_Factory.class);
        CopyService mockCopyService = mock(CopyService.class);
        BookCopyRepository mockBookCopyRepository = mock(BookCopyRepository.class);

        BookService bookService = new BookService(mockDto_factory, mockCopyService, mockBookRepository, mockBookCopyRepository);


        BookDTO result = bookService.getBookDetails(anyInt());

        assertNull(result);
    }

}