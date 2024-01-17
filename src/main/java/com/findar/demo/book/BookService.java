package com.findar.demo.book;

import com.findar.demo.copy.CopyService;
import com.findar.demo.dto.DTO_Factory;
import com.findar.demo.dto.BookDTO;
import com.findar.demo.entity.Book;
import com.findar.demo.entity.BookCopyView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private DTO_Factory dto_factory;
    private final CopyService copyService;
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;

    public List<BookDTO> getSummaryOfAllBooks() {
        List<BookDTO> list = new ArrayList<>();
        for (Book b : bookRepository.findAll()) {
            BookDTO bookDTO = dto_factory.createDTO(b);

            int numberOfCopies = copyService.findNumberOfCopiesOfBook(b.getId());
            bookDTO.setNumberOfCopies(numberOfCopies);

            list.add(bookDTO);
        }
        return list;
    }

    public List<BookDTO> getAlternativeSummaryOfAllBooks() {
        List<BookDTO> list = new ArrayList<>();
        for (BookCopyView b : bookCopyRepository.findAllBooks()) {
            BookDTO bookDTO = dto_factory.createDTO(b);

            list.add(bookDTO);
        }
        return list;
    }

    public BookDTO getBookDetails(int bookId) {
        Book book =
                bookRepository
                        .findById(bookId)
                        .orElse(null);
        if (book != null) {
            return dto_factory.createDTO(book);
        }
        return null;
    }

}
