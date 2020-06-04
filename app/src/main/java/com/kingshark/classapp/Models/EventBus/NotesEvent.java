package com.kingshark.classapp.Models.EventBus;

import com.kingshark.classapp.Models.Notes;

import java.util.List;

public class NotesEvent {
    private Notes notesList;

    public NotesEvent(Notes notesList) {
        this.notesList = notesList;
    }

    public Notes getNotesList() {
        return notesList;
    }

    public void setNotesList(Notes notesList) {
        this.notesList = notesList;
    }
}
