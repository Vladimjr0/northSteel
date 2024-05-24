package com.shevcov.notes.controller;

import com.shevcov.notes.entity.Note;
import com.shevcov.notes.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Контроллер для работы с заметками")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    @Operation(summary = "Метод для получения списка всех заметок")
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(noteService.findAll());
    }

    @Operation(summary = "Метод для создания заметки")
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.save(note));
    }

    @Operation(summary = "Метод для получения информации о конкретной заметки по ID")
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.findById(id));
    }

    @Operation(summary = "Метод для получения отсортированного списка заметок по дате")
    @GetMapping("/sort")
    public ResponseEntity<List<Note>> getSortedNotes(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date){
        return ResponseEntity.ok(noteService.getNoteSortedByDate(date));
    }

    @Operation(summary = "Метод для удаления заметки по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        noteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Метод для обновления заметки по ID")
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note) {
        return ResponseEntity.ok(noteService.updateNote(id, note));
    }
}
