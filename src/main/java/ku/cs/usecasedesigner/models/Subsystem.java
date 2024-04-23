package ku.cs.usecasedesigner.models;

public class Subsystem {
    private double subsystem_id;
    private String subsystem_name;
    private double system_id;
    public Subsystem(double subsystem_id, String subsystem_name, double system_id) {
        this.subsystem_id = subsystem_id;
        this.subsystem_name = subsystem_name;
        this.system_id = system_id;
    }

    public double getSubsystem_id() {
        return subsystem_id;
    }

    public String getSubsystem_name() {
        return subsystem_name;
    }

    public double getSystem_id() {
        return system_id;
    }

    public void setSubsystem_id(double subsystem_id) {
        this.subsystem_id = subsystem_id;
    }

    public void setSubsystem_name(String subsystem_name) {
        this.subsystem_name = subsystem_name;
    }

    public void setSystem_id(double system_id) {
        this.system_id = system_id;
    }

    public void setSubsystem(String subsystem_name, double system_id) {
        this.subsystem_name = subsystem_name;
        this.system_id = system_id;
    }

    public void setSubsystem(double subsystem_id, String subsystem_name, double system_id) {
        this.subsystem_id = subsystem_id;
        this.subsystem_name = subsystem_name;
        this.system_id = system_id;
    }
}
