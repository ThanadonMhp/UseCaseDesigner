package ku.cs.usecasedesigner.models;

public class Note {
    private int subSystemID;
    private String note;

    public Note() {
        this.subSystemID = 0;
        this.note = "";
    }

    public Note(int subSystemID, String note) {
        this.subSystemID = subSystemID;
        this.note = note;
    }

    public int getSubSystemID() {
        return subSystemID;
    }

    public String getNote() {
        return note;
    }

    public void setSubSystemID(int subSystemID) {
        this.subSystemID = subSystemID;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String toString() {
        return "SubSystemID: " + subSystemID + ", Note: " + note;
    }
}
