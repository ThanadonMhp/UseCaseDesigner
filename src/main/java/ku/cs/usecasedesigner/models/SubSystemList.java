package ku.cs.usecasedesigner.models;

import java.util.ArrayList;

public class SubSystemList {
    private ArrayList<SubSystem> subSystemList;

    public SubSystemList() {
        subSystemList = new ArrayList<SubSystem>();
    }

    public ArrayList<SubSystem> getSubSystemList() {
        return subSystemList;
    }

    public void addSubSystem(SubSystem subsystem) {
        subSystemList.add(subsystem);
    }

    public void removeSubSystem(SubSystem subsystem) {
        subSystemList.remove(subsystem);
    }

    public SubSystem findBySubSystemId(double subsystemId) {
        for (SubSystem subsystem : subSystemList) {
            if (subsystem.getSubSystemID() == subsystemId) {
                return subsystem;
            }
        }
        return null;
    }

    public int findLastSubSystemId() {
        int lastSubsystemId = 0;
        for (SubSystem subsystem : subSystemList) {
            if (subsystem.getSubSystemID() > lastSubsystemId) {
                lastSubsystemId = subsystem.getSubSystemID();
            }
        }
        return lastSubsystemId;
    }

    public void removeSubSystemByPositionID(int id) {
        for (SubSystem subsystem : subSystemList) {
            if (subsystem.getPositionID() == id) {
                subSystemList.remove(subsystem);
                break;
            }
        }
    }

    public void clear() {
        subSystemList.clear();
    }

    public Integer findSubSystemIDByPositionID(int id) {
        for (SubSystem subsystem : subSystemList) {
            if (subsystem.getPositionID() == id) {
                return subsystem.getSubSystemID();
            }
        }
        return null;
    }
}
