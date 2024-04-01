package ku.cs.usecasedesigner.models;

public class subsystem {
    private String subsystem_id;
    private String subsystem_name;
    private String system_id;
    public subsystem(String subsystem_id, String subsystem_name, String system_id) {
        this.subsystem_id = subsystem_id;
        this.subsystem_name = subsystem_name;
        this.system_id = system_id;
    }
}
