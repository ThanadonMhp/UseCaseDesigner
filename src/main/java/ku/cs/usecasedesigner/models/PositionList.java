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


    public Position findByPosition(double layoutX, double layoutY) {
        for (Position position : positionList) {
            if (position.getXPosition() == layoutX && position.getYPosition() == layoutY) {
                return position;
            }
        }
        return null;
    }

    public ArrayList<Position> findBySubSystemID(int subSystemID) {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (Position position : positionList) {
            if (position.getSubSystemID() == subSystemID) {
                positions.add(position);
            }
        }
        return positions;
    }

    public void updatePosition(int id, double newX, double newY) {
        for (Position position : positionList) {
            if (position.getPositionID() == id) {
                position.setXPosition(newX);
                position.setYPosition(newY);
            }
        }
    }

    public void updateSize(int id, double newWidth, double newHeight) {
        for (Position position : positionList) {
            if (position.getPositionID() == id) {
                position.setFitWidth(newWidth);
                position.setFitHeight(newHeight);
            }
        }
    }

    public void removePositionByID(int id) {
        for (Position position : positionList) {
            if (position.getPositionID() == id) {
                positionList.remove(position);
                break;
            }
        }
    }

    public void updateRotation(int id, double angle) {
        for (Position position : positionList) {
            if (position.getPositionID() == id) {
                position.setRotation(angle);
            }
        }
    }

    public void clear() {
        positionList.clear();
    }
}
