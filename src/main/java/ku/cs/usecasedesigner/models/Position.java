package ku.cs.usecasedesigner.models;

public class Position {
    private String type;
    private int position_id;
    private int symbol_id;
    private double x_position;
    private double y_position;
    private double fit_width;
    private double fit_height;
    private double rotation;
    public Position(int position_id, int symbol_id, double x_position, double y_position, double fit_width, double fit_height, double rotation) {
        this.type = "position";
        this.position_id = position_id;
        this.symbol_id = symbol_id;
        this.x_position = x_position;
        this.y_position = y_position;
        this.fit_width = fit_width;
        this.fit_height = fit_height;
        this.rotation = rotation;
    }

    public int getPosition_id() {
        return position_id;
    }

    public int getSymbol_id() {
        return symbol_id;
    }

    public double getX_position() {
        return x_position;
    }

    public double getY_position() {
        return y_position;
    }

    public double getFit_width() {
        return fit_width;
    }

    public double getFit_height() {
        return fit_height;
    }

    public double getRotation() {
        return rotation;
    }

}
