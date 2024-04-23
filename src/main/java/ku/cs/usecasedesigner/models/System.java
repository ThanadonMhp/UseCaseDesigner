package ku.cs.usecasedesigner.models;

public class System {
    private double system_id;
    private String system_name;
    public System(double system_id, String system_name) {
        this.system_id = system_id;
        this.system_name = system_name;
    }

    public double getSystem_id() {
        return system_id;
    }

    public String getSystem_name() {
        return system_name;
    }

    public void setSystem_id(double system_id) {
        this.system_id = system_id;
    }

    public void setSystem_name(String system_name) {
        this.system_name = system_name;
    }

    public void setSystem(String system_name) {
        this.system_name = system_name;
    }

    public void setSystem(double system_id, String system_name) {
        this.system_id = system_id;
        this.system_name = system_name;
    }

}
