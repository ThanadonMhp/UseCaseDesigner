package ku.cs.usecasedesigner.models;

public class UseCaseDetail {
    private int useCaseID;
    private String type;
    private int number;
    private String detail;

    public UseCaseDetail(int useCaseID, String type, int number, String detail) {
        this.useCaseID = useCaseID;
        this.type = type;
        this.number = number;
        this.detail = detail;
    }

    public int getUseCaseID() {
        return useCaseID;
    }

    public String getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public String getDetail() {
        return detail;
    }

    public void setUseCaseID(int useCaseID) {
        this.useCaseID = useCaseID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
