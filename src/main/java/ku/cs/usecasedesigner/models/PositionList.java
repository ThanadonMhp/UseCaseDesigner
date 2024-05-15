package ku.cs.usecasedesigner.models;

import java.util.ArrayList;

public class PositionList {
    private ArrayList<Position> positionList;

    public PositionList() {
        positionList = new ArrayList<Position>();
    }

    public ArrayList<Position> getPositionList() {
        return positionList;
    }

    public void addPosition(Position position) {
        positionList.add(position);
    }

    public void removePosition(Position position) {
        positionList.remove(position);
    }

    public Position findByPositionId(double positionId) {
        for (Position position : positionList) {
            if (position.getPositionID() == positionId) {
                return position;
            }
        }
        return null;
    }

    public int findLastPositionId() {
        int lastPositionId = 0;
        for (Position position : positionList) {
            if (position.getPositionID() > lastPositionId) {
                lastPositionId = position.getPositionID();
            }
        }
        return lastPositionId;
    }



}
