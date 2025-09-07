package com.example.appnotas;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotesViewModel extends ViewModel {
    private final List<String> notes = new ArrayList<>();

    public void addNote(String note){
        notes.add(note);
    }

    public void clearNotes(){
        notes.clear();
    }

    public List<String> getNotes(){
        return notes;
    }

    public int getCount() {
        return notes.size();
    }

}
