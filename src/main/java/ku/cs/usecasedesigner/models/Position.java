package ku.cs.usecasedesigner.models;

public class Position {
    private double position_id;
    private double symbol_id;
    private double x_position;
    private double y_position;
    private double size;
    public Position(double position_id, double symbol_id, double x_position, double y_position, double size) {
        this.position_id = position_id;
        this.symbol_id = symbol_id;
        this.x_position = x_position;
        this.y_position = y_position;
        this.size = size;
    }

    public double getPosition_id() {
        return position_id;
    }

    public double getSymbol_id() {
        return symbol_id;
    }

    public double getX_position() {
        return x_position;
    }

    public double getY_position() {
        return y_position;
    }

    public double getSize() {
        return size;
    }

    public void setX_position(double x_position) {
        this.x_position = x_position;
    }

    public void setY_position(double y_position) {
        this.y_position = y_position;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setPosition_id(double position_id) {
        this.position_id = position_id;
    }

    public void setSymbol_id(double symbol_id) {
        this.symbol_id = symbol_id;
    }

    public void setPosition(double x_position, double y_position) {
        this.x_position = x_position;
        this.y_position = y_position;
    }

    public void setPosition(double x_position, double y_position, double size) {
        this.x_position = x_position;
        this.y_position = y_position;
        this.size = size;
    }

    public void setPosition(double x_position, double y_position, double size, double position_id) {
        this.x_position = x_position;
        this.y_position = y_position;
        this.size = size;
        this.position_id = position_id;
    }

}
