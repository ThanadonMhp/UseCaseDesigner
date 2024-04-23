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

    public List<Subsystem> getSubsystems() {
        return Subsystems;
    }

    public List<Symbol> getSymbols() {
        return Symbols;
    }

    public List<Position> getPositions() {
        return Positions;
    }

    public void setSubsystems(List<Subsystem> Subsystems) {
        this.Subsystems = Subsystems;
    }

    public void setSymbols(List<Symbol> Symbols) {
        this.Symbols = Symbols;
    }

    public void setPositions(List<Position> Positions) {
        this.Positions = Positions;
    }

    public void addSubsystem(Subsystem subsystem) {
        Subsystems.add(subsystem);
    }

    public void addSymbol(Symbol symbol) {
        Symbols.add(symbol);
    }

    public void addPosition(Position position) {
        Positions.add(position);
    }

    public void removeSubsystem(Subsystem subsystem) {
        Subsystems.remove(subsystem);
    }

    public void removeSymbol(Symbol symbol) {
        Symbols.remove(symbol);
    }

    public void removePosition(Position position) {
        Positions.remove(position);
    }

    public Subsystem getSubsystemById(String subsystem_id) {
        for(Subsystem subsystem : Subsystems) {
            if(subsystem.getSubsystem_id().equals(subsystem_id)) {
                return subsystem;
            }
        }
        return null;
    }

    public Symbol getSymbolById(String symbol_id) {
        for(Symbol symbol : Symbols) {
            if(symbol.getSymbol_id().equals(symbol_id)) {
                return symbol;
            }
        }
        return null;
    }

    public Position getPositionById(String position_id) {
        for(Position position : Positions) {
            if(position.getPosition_id().equals(position_id)) {
                return position;
            }
        }
        return null;
    }

    public void updateSubsystem(Subsystem subsystem) {
        for(int i = 0; i < Subsystems.size(); i++) {
            if(Subsystems.get(i).getSubsystem_id().equals(subsystem.getSubsystem_id())) {
                Subsystems.set(i, subsystem);
                return;
            }
        }
    }

    public void updateSymbol(Symbol symbol) {
        for(int i = 0; i < Symbols.size(); i++) {
            if(Symbols.get(i).getSymbol_id().equals(symbol.getSymbol_id())) {
                Symbols.set(i, symbol);
                return;
            }
        }
    }

    public void updatePosition(Position position) {
        for(int i = 0; i < Positions.size(); i++) {
            if(Positions.get(i).getPosition_id().equals(position.getPosition_id())) {
                Positions.set(i, position);
                return;
            }
        }
    }

    public void deleteSubsystemById(String subsystem_id) {
        for(int i = 0; i < Subsystems.size(); i++) {
            if(Subsystems.get(i).getSubsystem_id().equals(subsystem_id)) {
                Subsystems.remove(i);
                return;
            }
        }
    }

    public void deleteSymbolById(String symbol_id) {
        for(int i = 0; i < Symbols.size(); i++) {
            if(Symbols.get(i).getSymbol_id().equals(symbol_id)) {
                Symbols.remove(i);
                return;
            }
        }
    }

    public void deletePositionById(String position_id) {
        for(int i = 0; i < Positions.size(); i++) {
            if(Positions.get(i).getPosition_id().equals(position_id)) {
                Positions.remove(i);
                return;
            }
        }
    }

    public void deleteAll() {
        Subsystems.clear();
        Symbols.clear();
        Positions.clear();
    }
}
