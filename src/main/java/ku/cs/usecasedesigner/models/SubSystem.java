package ku.cs.usecasedesigner.models;

public class SubSystem {
    private int subSystemID;
    private String subSystemName;
    private String note;
    private int positionID;

    public SubSystem(int subSystemID, String subSystemName, int positionID) {
        this.subSystemID = subSystemID;
        this.subSystemName = subSystemName;
        this.note = "!@#$%^&*()_+";
        this.positionID = positionID;
    }

    public SubSystem(int subSystemID, String subSystemName, String note, int positionID) {
        this.subSystemID = subSystemID;
        this.subSystemName = subSystemName;
        this.note = note;
        this.positionID = positionID;
    }

    public int getSubSystemID() {
        return subSystemID;
    }


    public String getSubSystemName() {
        return subSystemName;
    }

    public String getNote() {
        return note;
    }

    public void setSubSystemID(int subSystemID) {
        this.subSystemID = subSystemID;
    }

    public void setSubSystemName(String subSystemName) {
        this.subSystemName = subSystemName;
    }

    public int getPositionID() {
        return positionID;
    }

    public void setNote (String note) {
        this.note = note;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }
}
