package com.findar.demo.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findar.demo.copy.CopyRepository;
import com.findar.demo.copy_status.CopyStatusRepository;
import com.findar.demo.dto.BookDTO;
import com.findar.demo.entity.Book;
import com.findar.demo.entity.Copy;
import com.findar.demo.entity.CopyStatus;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookRestControllerHttpIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private CopyStatusRepository copyStatusRepository;

    @Test
    void when_NoBooks_expect_EmptyListOfBookDTO() {
        copyRepository.deleteAll();
        bookRepository.deleteAll();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/book", String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals("[]", responseEntity.getBody());
    }

    @Test
    void when_ThreeBooks_expect_GetSummaryOfAllBooksReturnsListFromBookService() throws Exception {
        CopyStatus available = copyStatusRepository.findById(1).get();
        List<Book> books =
                Arrays.asList(
                        new Book(0, "Title 1", "Author 1", "ISBN 1"),
                        new Book(0, "Title 2", "Author 2", "ISBN 2"),
                        new Book(0, "Title 3", "Author 3", "ISBN 3")
                );

        copyRepository.deleteAll();
        bookRepository.deleteAll();
        books = (List<Book>) bookRepository.saveAll(books);

        List<Copy> copies =
                Arrays.asList(
                        new Copy(0, books.get(0).getId(), available.getId()),
                        new Copy(0, books.get(0).getId(), available.getId()),
                        new Copy(0, books.get(1).getId(), available.getId())
                );

        copyRepository.saveAll(copies);

        List<BookDTO> expectedBookDTOList =
                Arrays.asList(
                        new BookDTO(books.get(0).getId(), "Title 1", "Author 1", "ISBN 1", 2, null),
                        new BookDTO(books.get(1).getId(), "Title 2", "Author 2", "ISBN 2", 1, null),
                        new BookDTO(books.get(2).getId(), "Title 3", "Author 3", "ISBN 3", 0, null)
                );
        String expectedJson = new ObjectMapper().writeValueAsString(expectedBookDTOList);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/book", String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        JSONAssert.assertEquals(expectedJson, responseEntity.getBody(), false);
    }

    @Test
    void when_BookDoesNotExist_expect_Null() {
        copyRepository.deleteAll();
        bookRepository.deleteAll();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/book/10", String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

}