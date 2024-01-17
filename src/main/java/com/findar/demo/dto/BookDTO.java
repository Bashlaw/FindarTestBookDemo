package com.findar.demo.dto;

import lombok.*;

import java.util.Collection;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BookDTO {

    private final int id;

    private final String title;

    private final String author;

    private final String isbn;

    private int numberOfCopies;

    private Collection<CopyDTO> copies;

}
