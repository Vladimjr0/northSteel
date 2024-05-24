package com.shevcov.notes.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    public Note(){
    }
    public Note(String content) {
        this.content = content;
    }

    private LocalDate createdDate;

}
