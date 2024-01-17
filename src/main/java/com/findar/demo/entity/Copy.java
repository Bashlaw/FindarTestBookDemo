package com.findar.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
@Setter
public class Copy {

    @Id
    private final int id;

    private final int bookId;

    @Setter
    private int statusId;

}

