package ku.cs.usecasedesigner.models;

import java.util.ArrayList;

public class SubsystemList {
    private ArrayList<Subsystem> subsystemList;

    public SubsystemList() {
        subsystemList = new ArrayList<Subsystem>();
    }

    public ArrayList<Subsystem> getSubsystemList() {
        return subsystemList;
    }

    public void addSubsystem(Subsystem subsystem) {
        subsystemList.add(subsystem);
    }

    public void removeSubsystem(Subsystem subsystem) {
        subsystemList.remove(subsystem);
    }

    public Subsystem findBySubsystemId(double subsystemId) {
        for (Subsystem subsystem : subsystemList) {
            if (subsystem.getSubsystem_id() == subsystemId) {
                return subsystem;
            }
        }
        return null;
    }

    public Subsystem findBySystemId(double systemId) {
        for (Subsystem subsystem : subsystemList) {
            if (subsystem.getSystem_id() == systemId) {
                return subsystem;
            }
        }
        return null;
    }

}
