package ku.cs.usecasedesigner.models;

public class Subsystem {
    private int subSystemID;
    private int systemID;
    private String subSystemName;

    public Subsystem(int subSystemID, int systemID, String subSystemName) {
        this.subSystemID = subSystemID;
        this.systemID = systemID;
        this.subSystemName = subSystemName;
    }

    public int getSubSystemID() {
        return subSystemID;
    }

    public int getSystemID() {
        return systemID;
    }

    public String getSubSystemName() {
        return subSystemName;
    }

    public void setSubSystemID(int subSystemID) {
        this.subSystemID = subSystemID;
    }

    public void setSystemID(int systemID) {
        this.systemID = systemID;
    }

    public void setSubSystemName(String subSystemName) {
        this.subSystemName = subSystemName;
    }

    public void setSubSystem(int systemID, String subSystemName) {
        this.systemID = systemID;
        this.subSystemName = subSystemName;
    }

    public void setSubSystem(int subSystemID, int systemID, String subSystemName) {
        this.subSystemID = subSystemID;
        this.systemID = systemID;
        this.subSystemName = subSystemName;
    }
}
