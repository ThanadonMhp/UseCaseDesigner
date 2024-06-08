package ku.cs.usecasedesigner.models;

public class UseCase {
    private int useCaseID;
    private String useCaseName;
    private String actorID;
    private String preCondition;
    private String description;
    private String postCondition;
    private String note;
    private int positionID;

    public UseCase(int useCaseID, String useCaseName, String actorID, String preCondition, String description, String postCondition, String note, int positionID) {
        this.useCaseID = useCaseID;
        this.useCaseName = useCaseName;
        this.actorID = actorID;
        this.preCondition = preCondition;
        this.description = description;
        this.postCondition = postCondition;
        this.note = note;
        this.positionID = positionID;
    }

    public UseCase(int useCaseID, String useCaseName, String note, int positionID) {
        this.useCaseID = useCaseID;
        this.useCaseName = useCaseName;
        this.actorID = "0";
        this.preCondition = "!@#$%^&*()_+";
        this.description = "!@#$%^&*()_+";
        this.postCondition = "!@#$%^&*()_+";
        this.note = note;
        this.positionID = positionID;
    }

    public int getUseCaseID() {
        return useCaseID;
    }

    public String getUseCaseName() {
        return useCaseName;
    }

    public String getActorID() {
        return actorID;
    }

    public String getPreCondition() {
        return preCondition;
    }

    public String getDescription() {
        return description;
    }

    public String getPostCondition() {
        return postCondition;
    }

    public String getNote() {
        return note;
    }

    public int getPositionID() {
        return positionID;
    }

    public void setUseCaseID(int useCaseID) {
        this.useCaseID = useCaseID;
    }

    public void setUseCaseName(String useCaseName) {
        this.useCaseName = useCaseName;
    }

    public void setActorID(String actorID) {
        this.actorID = actorID;
    }

    public void setPreCondition(String preCondition) {
        this.preCondition = preCondition;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPostCondition(String postCondition) {
        this.postCondition = postCondition;
    }

    public void setNote (String note) {
        this.note = note;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

}
