package ku.cs.usecasedesigner.models;

public class SubSystem {
    private int subSystemID;
    private String subSystemName;
    private int positionID;

    public SubSystem(int subSystemID, String subSystemName, int positionID) {
        this.subSystemID = subSystemID;
        this.subSystemName = subSystemName;
        this.positionID = positionID;
    }

    public int getSubSystemID() {
        return subSystemID;
    }


    public String getSubSystemName() {
        return subSystemName;
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

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }
}
