package ku.cs.usecasedesigner.models;

import java.util.ArrayList;

public class SystemList {
    private ArrayList<System> systemList;

    public SystemList() {
        systemList = new ArrayList<System>();
    }

    public ArrayList<System> getSystemList() {
        return systemList;
    }

    public void addSystem(System system) {
        systemList.add(system);
    }

    public void removeSystem(System system) {
        systemList.remove(system);
    }

    public System findBySystemId(double systemId) {
        for (System system : systemList) {
            if (system.getSystem_id() == systemId) {
                return system;
            }
        }
        return null;
    }
}
