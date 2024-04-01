package ku.cs.usecasedesigner.models;

public class symbol {
    private String symbol_id;
    private String subsystem_id;
    private String symbol_type;
    private String label;
    public symbol(String symbol_id, String subsystem_id, String symbol_type, String label) {
        this.symbol_id = symbol_id;
        this.subsystem_id = subsystem_id;
        this.symbol_type = symbol_type;
        this.label = label;
    }
}
