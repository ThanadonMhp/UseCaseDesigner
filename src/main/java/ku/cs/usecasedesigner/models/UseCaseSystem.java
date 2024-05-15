package ku.cs.usecasedesigner.models;

public class UseCaseSystem {
    private double systemID;
    private String systemName;

    public UseCaseSystem(double systemID, String systemName) {
        this.systemID = systemID;
        this.systemName = systemName;
    }

    public double getSystemID() {
        return systemID;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemID(double systemID) {
        this.systemID = systemID;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

}
