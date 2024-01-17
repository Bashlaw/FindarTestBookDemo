package com.findar.demo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
public class CopyStatus {

    @Id
    private int id;

    private String status;

}
