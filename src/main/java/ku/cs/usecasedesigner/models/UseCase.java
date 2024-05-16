package ku.cs.usecasedesigner.models;

public class UseCase {
    private int useCaseID;
    private String useCaseName;
    private int actorID;
    private String preCondition;
    private String description;
    private String postCondition;
    private int positionID;

    public UseCase(int useCaseID, String useCaseName, int actorID, String preCondition, String description, String postCondition, int positionID) {
        this.useCaseID = useCaseID;
        this.useCaseName = useCaseName;
        this.actorID = actorID;
        this.preCondition = preCondition;
        this.description = description;
        this.postCondition = postCondition;
        this.positionID = positionID;
    }

    public UseCase(int useCaseID, int positionID, String text) {
        this.useCaseID = useCaseID;
        this.positionID = positionID;
        this.useCaseName = text;
        this.actorID = 0;
        this.preCondition = "!@#$%^&*()_+";
        this.description = "!@#$%^&*()_+";
        this.postCondition = "!@#$%^&*()_+";
    }

    public int getUseCaseID() {
        return useCaseID;
    }

    public String getUseCaseName() {
        return useCaseName;
    }

    public int getActorID() {
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

    public int getPositionID() {
        return positionID;
    }

    public void setUseCaseID(int useCaseID) {
        this.useCaseID = useCaseID;
    }

    public void setUseCaseName(String useCaseName) {
        this.useCaseName = useCaseName;
    }

    public void setActorID(int actorID) {
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

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }
}
