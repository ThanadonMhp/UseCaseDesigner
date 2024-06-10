package ku.cs.usecasedesigner.models;

import java.util.ArrayList;

public class UseCaseDetailList {
    private ArrayList<UseCaseDetail> useCaseDetailList;

    public UseCaseDetailList() {
        useCaseDetailList = new ArrayList<UseCaseDetail>();
    }

    public void addUseCaseDetail(UseCaseDetail useCaseDetail) {
        useCaseDetailList.add(useCaseDetail);
    }

    public void removeUseCaseDetail(UseCaseDetail useCaseDetail) {
        useCaseDetailList.remove(useCaseDetail);
    }

    public ArrayList<UseCaseDetail> getUseCaseDetailList() {
        return useCaseDetailList;
    }

    public void setUseCaseDetailList(ArrayList<UseCaseDetail> useCaseDetailList) {
        this.useCaseDetailList = useCaseDetailList;
    }

    public UseCaseDetail getUseCaseDetail(int useCaseID) {
        for (UseCaseDetail useCaseDetail : useCaseDetailList) {
            if (useCaseDetail.getUseCaseID() == useCaseID) {
                return useCaseDetail;
            }
        }
        return null;
    }

    public UseCaseDetail findByUseCaseId(int useCaseId) {
        for (UseCaseDetail useCaseDetail : useCaseDetailList) {
            if (useCaseDetail.getUseCaseID() == useCaseId) {
                return useCaseDetail;
            }
        }
        return null;
    }

    public UseCaseDetail findByNumber (int number) {
        for (UseCaseDetail useCaseDetail : useCaseDetailList) {
            if (useCaseDetail.getNumber() == number) {
                return useCaseDetail;
            }
        }
        return null;
    }

    public void clear() {
        useCaseDetailList.clear();
    }

    // clear use case detail that contains use Case ID
    public void clearUseCaseDetail(int useCaseID) {
        useCaseDetailList.removeIf(useCaseDetail -> useCaseDetail.getUseCaseID() == useCaseID);
    }
}
