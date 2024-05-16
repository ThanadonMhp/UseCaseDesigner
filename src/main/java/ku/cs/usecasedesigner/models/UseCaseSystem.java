package ku.cs.usecasedesigner.models;

public class UseCaseSystem {
    private int systemID;
    private String systemName;

    public UseCaseSystem(int systemID, String systemName) {
        this.systemID = systemID;
        this.systemName = systemName;
    }

    public int getSystemID() {
        return systemID;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemID(int systemID) {
        this.systemID = systemID;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

}
