package com.shevcov.notes.service;

import com.shevcov.notes.entity.Note;
import com.shevcov.notes.repository.NoteRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;


    @Transactional(readOnly = true)
    public List<Note> findAll(){
        return noteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Note findById(Long id){
        return noteRepository.findById(id).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Заметка с таким ID не найдена"));
    }

    @Transactional
    public Note save(Note note){
        note.setCreatedDate(LocalDate.now());
        return noteRepository.save(note);
    }

    @Transactional
    public void deleteById(Long id){
        if (!noteRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Заметка с таким ID не найдена");
        }
        noteRepository.deleteById(id);
    }

    @Transactional
    public Note updateNote(Long id, Note note){
        Note existingNote = findById(id);
        existingNote.setContent(note.getContent());
        return noteRepository.save(existingNote);
    }

    public List<Note> getNoteSortedByDate(LocalDate date){
        return noteRepository.findNotesByCreatedDate(date);
    }

    //TODO упростить этот код
    @PostConstruct
    public void init(){
        if(noteRepository.count()==0){
            Note note = new Note("<p> Начальная заметка.</p>");
            note.setCreatedDate(LocalDate.now());
            noteRepository.save(note);
        }
    }
}
