package ku.cs.usecasedesigner.models;

import java.io.Serializable;
import java.util.List;

public class SystemState implements Serializable {
    private List<Subsystem> Subsystems;
    private List<Symbol> Symbols;
    private List<Position> Positions;

    public SystemState(List<Subsystem> Subsystems, List<Symbol> Symbols, List<Position> Positions) {
        this.Subsystems = Subsystems;
        this.Symbols = Symbols;
        this.Positions = Positions;
    }

}
