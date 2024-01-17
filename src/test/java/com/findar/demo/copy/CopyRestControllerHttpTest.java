package com.findar.demo.copy;

import com.findar.demo.dto.BookDTO;
import com.findar.demo.dto.CopyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CopyRestController.class)
class CopyRestControllerHttpTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CopyService mockCopyService;

    @Test
    void when_BorrowAvailableCopy_expect_NonNullCopyDTO() throws Exception {
        BookDTO bookDTO = new BookDTO(1, "Title 1", "Author 1", "ISBN 1", 1, null);
        CopyDTO copyDTO = new CopyDTO(1, bookDTO, "On loan");

        when(mockCopyService.borrowCopy(anyInt())).thenReturn(copyDTO);

        mockMvc
                .perform(post("/copy/4/borrow"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("On loan"));
    }

}