package com.findar.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    private final int id;

    private final String title;

    private final String author;

    private final String isbn;

}
