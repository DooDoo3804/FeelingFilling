package com.example.billing.data.loggingDB.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class TestDocument {

    @Id
    private String id;

    private String test;

    private Date createdAt;

    private Date updatedAt;
}
