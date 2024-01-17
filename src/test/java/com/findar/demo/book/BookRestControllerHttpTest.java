package com.findar.demo.book;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.findar.demo.dto.BookDTO;
import com.findar.demo.dto.CopyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookRestController.class)
class BookRestControllerHttpTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService mockBookService;

    @Test
    void when_NoBooks_expect_EmptyListOfBookDTO() throws Exception {
        when(mockBookService.getSummaryOfAllBooks()).thenReturn(new ArrayList<>());

        mockMvc
                .perform(get("/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().string("[]"));
    }

    @Test
    void when_ThreeBooks_expect_GetSummaryOfAllBooksReturnsListFromBookService() throws Exception {
        List<BookDTO> bookDTOList =
                Arrays.asList(
                        new BookDTO(1, "Title 1", "Author 1", "ISBN 1", 2, null),
                        new BookDTO(2, "Title 2", "Author 2", "ISBN 2", 4, null),
                        new BookDTO(3, "Title 3", "Author 3", "ISBN 3", 6, null)
                );
        String expectedJson = new ObjectMapper().writeValueAsString(bookDTOList);

        when(mockBookService.getSummaryOfAllBooks()).thenReturn(bookDTOList);

        mockMvc
                .perform(get("/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void when_BookExists_expect_NonNullBookDTO() throws Exception {
        BookDTO expectedBookDTO = getBookDTO();

        ObjectMapper mapper = new ObjectMapper();
        // tell the mapper to take notice of transient setting on fields not getters
        mapper.setVisibility(
                VisibilityChecker.Std.defaultInstance()
                        .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                        .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
        );

        String expectedJson = mapper.writeValueAsString(expectedBookDTO);

        when(mockBookService.getBookDetails(2)).thenReturn(expectedBookDTO);

        mockMvc
                .perform(get("/book/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(content().json(expectedJson));
    }

    private static BookDTO getBookDTO() {
        BookDTO expectedBookDTO =
                new BookDTO(
                        2,
                        "Title 2",
                        "Author 2",
                        "ISBN 2",
                        4,
                        null);

        List<CopyDTO> copyDTOs =
                Arrays.asList(
                        new CopyDTO(1, expectedBookDTO, "Available"),
                        new CopyDTO(2, expectedBookDTO, "Available"),
                        new CopyDTO(3, expectedBookDTO, "Available"),
                        new CopyDTO(4, expectedBookDTO, "Available")
                );

        expectedBookDTO.setCopies(copyDTOs);
        return expectedBookDTO;
    }

    @Test
    void when_BookExists_expect_NonNullBookDTO_version2() throws Exception {
        BookDTO expectedBookDTO =
                new BookDTO(
                        2,
                        "Title 2",
                        "Author 2",
                        "ISBN 2",
                        4,
                        null);

        when(mockBookService.getBookDetails(2)).thenReturn(expectedBookDTO);

        mockMvc
                .perform(get("/book/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.author").value("Author 2"))
                .andExpect(jsonPath("$.title").value("Title 2"))
                .andExpect(jsonPath("$.numberOfCopies").value(4))
                .andExpect(jsonPath("$.copies").doesNotExist())
                .andExpect(jsonPath("$.isbn").value("ISBN 2"));
    }

    @Test
    void when_BookDoesNotExist_expect_Null() throws Exception {
        when(mockBookService.getBookDetails(anyInt())).thenReturn(null);

        mockMvc
                .perform(get("/book/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

}