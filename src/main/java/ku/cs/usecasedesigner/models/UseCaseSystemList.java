package ku.cs.usecasedesigner.models;

import java.util.ArrayList;

public class UseCaseSystemList {
    private ArrayList<UseCaseSystem> useCaseSystemList;

    public UseCaseSystemList() {
        useCaseSystemList = new ArrayList<UseCaseSystem>();
    }

    public ArrayList<UseCaseSystem> getSystemList() {
        return useCaseSystemList;
    }

    public void addSystem(UseCaseSystem useCaseSystem) {
        useCaseSystemList.add(useCaseSystem);
    }

    public void removeSystem(UseCaseSystem useCaseSystem) {
        useCaseSystemList.remove(useCaseSystem);
    }

    public UseCaseSystem findBySystemId(double systemId) {
        for (UseCaseSystem useCaseSystem : useCaseSystemList) {
            if (useCaseSystem.getSystem_id() == systemId) {
                return useCaseSystem;
            }
        }
        return null;
    }

    public double findLastUseCaseSystemId() {
        if (useCaseSystemList.isEmpty()) {
            return 0;
        }
        return useCaseSystemList.get(useCaseSystemList.size() - 1).getSystem_id();
    }
}