package ku.cs.usecasedesigner.models;

public class Symbol {
    private double symbol_id;
    private double subsystem_id;
    private String symbol_type;
    private String label;
    public Symbol(double symbol_id, double subsystem_id, String symbol_type, String label) {
        this.symbol_id = symbol_id;
        this.subsystem_id = subsystem_id;
        this.symbol_type = symbol_type;
        this.label = label;
    }

    public Object getSymbol_id() {
        return symbol_id;
    }

    public Object getSubsystem_id() {
        return subsystem_id;
    }

    public Object getSymbol_type() {
        return symbol_type;
    }

    public Object getLabel() {
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