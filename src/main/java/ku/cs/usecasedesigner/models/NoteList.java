package ku.cs.usecasedesigner.models;

import java.util.ArrayList;

public class NoteList {
    private ArrayList<Note> noteList;

    public NoteList() {
        noteList = new ArrayList<Note>();
    }

    public ArrayList<Note> getNoteList() {
        return noteList;
    }

    public void addNote(Note note) {
        noteList.add(note);
    }

    public void removeNote(Note note) {
        noteList.remove(note);
    }

    public Note findBySubSystemID(double subSystemId) {
        for (Note note : noteList) {
            if (note.getSubSystemID() == subSystemId) {
                return note;
            }
        }
        return null;
    }

    public void updateNoteBySubSystemID(int subSystemID, String text) {
        for (Note note : noteList) {
            if (note.getSubSystemID() == subSystemID) {
                note.setNote(text);
            }
        }
    }
}
