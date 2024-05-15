package ku.cs.usecasedesigner.models;

public class Actor {
    private int actorID;
    private String actorName;
    private int positionID;

    public Actor(int actorID, String actorName, int positionID) {
        this.actorID = actorID;
        this.actorName = actorName;
        this.positionID = positionID;
    }

    public int getActorID() {
        return actorID;
    }

    public String getActorName() {
        return actorName;
    }

    public int getPositionID() {
        return positionID;
    }

    public void setActorID(int actorID) {
        this.actorID = actorID;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }
}
