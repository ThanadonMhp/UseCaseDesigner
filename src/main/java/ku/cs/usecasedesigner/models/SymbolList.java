package ku.cs.usecasedesigner.models;

import java.util.ArrayList;

public class SymbolList {
    private ArrayList<Symbol> symbolList;

    public SymbolList() {
        symbolList = new ArrayList<Symbol>();
    }

    public ArrayList<Symbol> getSymbolList() {
        return symbolList;
    }

    public void addSymbol(Symbol symbol) {
        symbolList.add(symbol);
    }

    public void removeSymbol(Symbol symbol) {
        symbolList.remove(symbol);
    }

    public Symbol findBySymbolId(double symbolId) {
        for (Symbol symbol : symbolList) {
            if (symbol.getSymbol_id() == symbolId) {
                return symbol;
            }
        }
        return null;
    }

    public Symbol findBySubsystemId(double subsystemId) {
        for (Symbol symbol : symbolList) {
            if (symbol.getSubsystem_id() == subsystemId) {
                return symbol;
            }
        }
        return null;
    }

    public int findLastSymbolId() {
        int lastSymbolId = 0;
        for (Symbol symbol : symbolList) {
            if (symbol.getSymbol_id() > lastSymbolId) {
                lastSymbolId = symbol.getSymbol_id();
            }
        }
        return lastSymbolId;
    }
}


