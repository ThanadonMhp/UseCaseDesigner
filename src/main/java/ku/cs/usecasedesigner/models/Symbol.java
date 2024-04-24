package ku.cs.usecasedesigner.models;

public class Symbol {
    private String type;
    private double symbol_id;
    private double subsystem_id;
    private String symbol_type;
    private String label;
    public Symbol(double symbol_id, double subsystem_id, String symbol_type, String label) {
        this.type = "symbol";
        this.symbol_id = symbol_id;
        this.subsystem_id = subsystem_id;
        this.symbol_type = symbol_type;
        this.label = label;
    }

    public double getSymbol_id() {
        return symbol_id;
    }

    public double getSubsystem_id() {
        return subsystem_id;
    }

    public String getSymbol_type() {
        return symbol_type;
    }

    public String getLabel() {
        return label;
    }

    public void setSymbol_id(double symbol_id) {
        this.symbol_id = symbol_id;
    }

    public void setSubsystem_id(double subsystem_id) {
        this.subsystem_id = subsystem_id;
    }

    public void setSymbol_type(String symbol_type) {
        this.symbol_type = symbol_type;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setSymbol(double subsystem_id, String symbol_type, String label) {
        this.subsystem_id = subsystem_id;
        this.symbol_type = symbol_type;
        this.label = label;
    }

    public void setSymbol(double symbol_id, double subsystem_id, String symbol_type, String label) {
        this.symbol_id = symbol_id;
        this.subsystem_id = subsystem_id;
        this.symbol_type = symbol_type;
        this.label = label;
    }
}
