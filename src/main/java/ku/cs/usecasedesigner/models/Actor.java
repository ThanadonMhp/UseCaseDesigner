package ku.cs.usecasedesigner.models;

public class Actor {
    private int actorID;
    private String actorName;
    private String alias;
    private String description;
    private String note;
    private int positionID;

    public Actor(int actorID, String actorName, String note, int positionID) {
        this.actorID = actorID;
        this.actorName = actorName;
        this.alias = "!@#$%^&*()_+";
        this.description = "!@#$%^&*()_+";
        this.note = note;
        this.positionID = positionID;
    }

    public Actor(int actorID, String actorName, String alias, String note, int positionID) {
        this.actorID = actorID;
        this.actorName = actorName;
        this.alias = alias;
        this.description = "!@#$%^&*()_+";
        this.note = note;
        this.positionID = positionID;
    }

    public Actor(int actorID, String actorName, String alias, String description, String note, int positionID) {
        this.actorID = actorID;
        this.actorName = actorName;
        this.alias = alias;
        this.description = description;
        this.note = note;
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

    public String getAlias() {
        return alias;
    }

    public String getDescription() {
        return description;
    }

    public String getNote() {
        return note;
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

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
