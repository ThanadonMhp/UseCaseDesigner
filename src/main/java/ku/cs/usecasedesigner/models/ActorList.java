package ku.cs.usecasedesigner.models;

import java.util.ArrayList;

public class ActorList {
    private ArrayList<Actor> actorList;

    public ActorList() {
        actorList = new ArrayList<Actor>();
    }

    public ArrayList<Actor> getActorList() {
        return actorList;
    }

    public void addActor(Actor actor) {
        actorList.add(actor);
    }

    public void removeActor(Actor actor) {
        actorList.remove(actor);
    }

    public Actor findByActorId(int actorId) {
        for (Actor actor : actorList) {
            if (actor.getActorID() == actorId) {
                return actor;
            }
        }
        return null;
    }

    public int findLastActorId() {
        int lastActorId = 0;
        for (Actor actor : actorList) {
            if (actor.getActorID() > lastActorId) {
                lastActorId = actor.getActorID();
            }
        }
        return lastActorId;
    }

    public Actor findByPositionId(int positionId) {
        for (Actor actor : actorList) {
            if (actor.getPositionID() == positionId) {
                return actor;
            }
        }
        return null;
    }

    public Actor findByActorName(String actorName) {
        for (Actor actor : actorList) {
            if (actor.getActorName().equals(actorName)) {
                return actor;
            }
        }
        return null;
    }

    public void removeActorByPositionID(int id) {
        for (Actor actor : actorList) {
            if (actor.getPositionID() == id) {
                actorList.remove(actor);
                break;
            }
        }
    }

    public void clear() {
        actorList.clear();
    }
}
