package ku.cs.usecasedesigner.models;

import java.util.ArrayList;

public class UseCaseList {
    private ArrayList<UseCase> useCaseList;

    public UseCaseList() {
        useCaseList = new ArrayList<UseCase>();
    }

    public ArrayList<UseCase> getSymbolList() {
        return useCaseList;
    }

    public void addSymbol(UseCase useCase) {
        useCaseList.add(useCase);
    }

    public void removeSymbol(UseCase useCase) {
        useCaseList.remove(useCase);
    }

    public UseCase findBySymbolId(double symbolId) {
        for (UseCase useCase : useCaseList) {
            if (useCase.getUseCaseID() == symbolId) {
                return useCase;
            }
        }
        return null;
    }

    public int findLastSymbolId() {
        int lastSymbolId = 0;
        for (UseCase useCase : useCaseList) {
            if (useCase.getUseCaseID() > lastSymbolId) {
                lastSymbolId = useCase.getUseCaseID();
            }
        }
        return lastSymbolId;
    }

}


