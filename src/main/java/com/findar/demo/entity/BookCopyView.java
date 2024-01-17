package com.findar.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
public class BookCopyView {

    @Id
    private final int id;

    private final String title;

    private final String author;

    private final String isbn;

    private final int numberOfCopies;

}
