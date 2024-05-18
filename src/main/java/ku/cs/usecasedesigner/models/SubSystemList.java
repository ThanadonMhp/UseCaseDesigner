package ku.cs.usecasedesigner.models;

import java.util.ArrayList;

public class SubSystemList {
    private ArrayList<SubSystem> subSystemList;

    public SubSystemList() {
        subSystemList = new ArrayList<SubSystem>();
    }

    public ArrayList<SubSystem> getSubsystemList() {
        return subSystemList;
    }

    public void addSubsystem(SubSystem subsystem) {
        subSystemList.add(subsystem);
    }

    public void removeSubsystem(SubSystem subsystem) {
        subSystemList.remove(subsystem);
    }

    public SubSystem findBySubsystemId(double subsystemId) {
        for (SubSystem subsystem : subSystemList) {
            if (subsystem.getSubSystemID() == subsystemId) {
                return subsystem;
            }
        }
        return null;
    }

    public int findLastSubsystemId() {
        int lastSubsystemId = 0;
        for (SubSystem subsystem : subSystemList) {
            if (subsystem.getSubSystemID() > lastSubsystemId) {
                lastSubsystemId = subsystem.getSubSystemID();
            }
        }
        return lastSubsystemId;
    }

}
