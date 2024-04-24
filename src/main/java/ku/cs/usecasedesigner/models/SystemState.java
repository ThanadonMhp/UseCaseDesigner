package ku.cs.usecasedesigner.models;

import java.io.Serializable;
import java.util.List;

public class SystemState implements Serializable {
    private List<Subsystem> Subsystems;
    private List<Symbol> Symbols;
    private List<Position> Positions;

}
