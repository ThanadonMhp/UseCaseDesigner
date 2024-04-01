package ku.cs.usecasedesigner.models;

public class position {
    private String position_id;
    private String symbol_id;
    private double x_position;
    private double y_position;
    private double size;
    public position(String position_id, String symbol_id, double x_position, double y_position, double size) {
        this.position_id = position_id;
        this.symbol_id = symbol_id;
        this.x_position = x_position;
        this.y_position = y_position;
        this.size = size;
    }
}
