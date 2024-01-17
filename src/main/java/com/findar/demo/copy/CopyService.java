package com.findar.demo.copy;

import com.findar.demo.book.BookRepository;
import com.findar.demo.dto.BookDTO;
import com.findar.demo.dto.DTO_Factory;
import com.findar.demo.copy_status.CopyStatusService;
import com.findar.demo.dto.CopyDTO;
import com.findar.demo.entity.Book;
import com.findar.demo.entity.Copy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CopyService {

    private final DTO_Factory dto_factory;
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;
    private final CopyStatusService copyStatusService;

    public CopyDTO returnCopy(int copyId) {

        //check if book exists and on loan
        int onLoanId = copyStatusService.getByStatus("On loan").getId();

        if (copyRepository.existsByIdAndStatusId(copyId, onLoanId)) {

            Copy copy = copyRepository.findById(copyId).orElse(null);
            assert copy != null;
            copy.setStatusId(copyStatusService.getByStatus("Available").getId());

            copy = copyRepository.save(copy);

            return getCopyDTO(copy);
        }

        return null;

    }

    public CopyDTO borrowCopy(int copyId) {
        int availableId = copyStatusService.getByStatus("Available").getId();
        int onLoanId = copyStatusService.getByStatus("On loan").getId();
        Copy copy = copyRepository.findById(copyId).orElse(null);

        if (copy != null && copy.getStatusId() == availableId) {
            copy.setStatusId(onLoanId);
            copy = copyRepository.save(copy);

            return getCopyDTO(copy);
        }

        return null;
    }

    public Collection<CopyDTO> findCopiesOfBook(BookDTO book) {
        return copyRepository
                .streamAllByBookId(book.getId())
                .map(copy ->
                        dto_factory.createDTO(
                                copy,
                                book,
                                copyStatusService.getById(copy.getStatusId()).getStatus()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public int findNumberOfCopiesOfBook(int bookId) {
        return copyRepository.countByBookId(bookId);
    }

    private CopyDTO getCopyDTO(Copy copy) {
        BookDTO bookDTO = getBookDtoForCopy(copy);
        String copyStatus = copyStatusService.getById(copy.getStatusId()).getStatus();
        return dto_factory.createDTO(copy, bookDTO, copyStatus);
    }

    private BookDTO getBookDtoForCopy(Copy copy) {
        Book book =
                bookRepository
                        .findById(copy.getBookId())
                        .get();
        return dto_factory.createDTO(book);
    }

}
