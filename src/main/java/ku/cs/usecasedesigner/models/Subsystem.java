package ku.cs.usecasedesigner.models;

public class Subsystem {
    private String subsystem_id;
    private String subsystem_name;
    private String system_id;
    public Subsystem(String subsystem_id, String subsystem_name, String system_id) {
        this.subsystem_id = subsystem_id;
        this.subsystem_name = subsystem_name;
        this.system_id = system_id;
    }

    public Object getSubsystem_id() {
        return subsystem_id;
    }

    public Object getSubsystem_name() {
        return subsystem_name;
    }

    public Object getSystem_id() {
        return system_id;
    }

    public void setSubsystem_id(String subsystem_id) {
        this.subsystem_id = subsystem_id;
    }

    public void setSubsystem_name(String subsystem_name) {
        this.subsystem_name = subsystem_name;
    }

    public void setSystem_id(String system_id) {
        this.system_id = system_id;
    }

    public void setSubsystem(String subsystem_name, String system_id) {
        this.subsystem_name = subsystem_name;
        this.system_id = system_id;
    }

    public void setSubsystem(String subsystem_id, String subsystem_name, String system_id) {
        this.subsystem_id = subsystem_id;
        this.subsystem_name = subsystem_name;
        this.system_id = system_id;
    }
}
