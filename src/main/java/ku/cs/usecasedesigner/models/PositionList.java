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
            if (position.getPosition_id() == positionId) {
                return position;
            }
        }
        return null;
    }

    public Position findBySymbolId(double symbolId) {
        for (Position position : positionList) {
            if (position.getSymbol_id() == symbolId) {
                return position;
            }
        }
        return null;
    }

    public double findLastPositionId() {
        double lastPositionId = 0;
        for (Position position : positionList) {
            if (position.getPosition_id() > lastPositionId) {
                lastPositionId = position.getPosition_id();
            }
        }
        return lastPositionId;
    }

}
