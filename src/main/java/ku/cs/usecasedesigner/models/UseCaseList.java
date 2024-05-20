package ku.cs.usecasedesigner.models;

import java.util.ArrayList;

public class UseCaseList {
    private ArrayList<UseCase> useCaseList;

    public UseCaseList() {
        useCaseList = new ArrayList<UseCase>();
    }

    public void addUseCase(UseCase useCase) {
        useCaseList.add(useCase);
    }

    public void removeUseCase(UseCase useCase) {
        useCaseList.remove(useCase);
    }

    public ArrayList<UseCase> getUseCaseList() {
        return useCaseList;
    }

    public void setUseCaseList(ArrayList<UseCase> useCaseList) {
        this.useCaseList = useCaseList;
    }

    public UseCase getUseCase(int useCaseID) {
        for (UseCase useCase : useCaseList) {
            if (useCase.getUseCaseID() == useCaseID) {
                return useCase;
            }
        }
        return null;
    }

    public int findLastUseCaseId() {
        int lastUseCaseId = 0;
        for (UseCase useCase : useCaseList) {
            if (useCase.getUseCaseID() > lastUseCaseId) {
                lastUseCaseId = useCase.getUseCaseID();
            }
        }
        return lastUseCaseId;
    }

    public UseCase findByUseCaseId(int useCaseId) {
        for (UseCase useCase : useCaseList) {
            if (useCase.getUseCaseID() == useCaseId) {
                return useCase;
            }
        }
        return null;
    }

    public void removeUseCaseByPositionID(int id) {
        for (UseCase useCase : useCaseList) {
            if (useCase.getPositionID() == id) {
                useCaseList.remove(useCase);
                break;
            }
        }
    }

    public void clear() {
        useCaseList.clear();
    }
}


