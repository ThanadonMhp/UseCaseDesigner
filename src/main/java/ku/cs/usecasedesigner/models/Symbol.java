package ku.cs.usecasedesigner.models;

public class Symbol {
    private String type;
    private int symbol_id;
    private int subsystem_id;
    private String symbol_type;
    private int usecase_id;
    private String label;
    private String description;
    public Symbol(int symbol_id, int subsystem_id, String symbol_type,int usecase_id, String label, String description) {
        this.type = "symbol";
        this.symbol_id = symbol_id;
        this.subsystem_id = subsystem_id;
        this.symbol_type = symbol_type;
        this.usecase_id = usecase_id;
        this.label = label;
        this.description = description;
    }

    public int getSymbol_id() {
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

    public void setSymbol_id(int symbol_id) {
        this.symbol_id = symbol_id;
    }

    public void setSubsystem_id(int subsystem_id) {
        this.subsystem_id = subsystem_id;
    }

    public void setSymbol_type(String symbol_type) {
        this.symbol_type = symbol_type;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setSymbol(int subsystem_id, String symbol_type, String label) {
        this.subsystem_id = subsystem_id;
        this.symbol_type = symbol_type;
        this.label = label;
    }

    public void setSymbol(int symbol_id, int subsystem_id, String symbol_type, String label) {
        this.symbol_id = symbol_id;
        this.subsystem_id = subsystem_id;
        this.symbol_type = symbol_type;
        this.label = label;
    }

    public int getUsecase_id() {
        return usecase_id;
    }

    public String getDescription() {
        return description;
    }
}
