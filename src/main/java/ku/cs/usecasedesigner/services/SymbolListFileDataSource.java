package ku.cs.usecasedesigner.services;

import ku.cs.usecasedesigner.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SymbolListFileDataSource implements DataSource<SymbolList>, ManageDataSource<Symbol>{
    private String directory;
    private String fileName;

    public SymbolListFileDataSource(String directory, String fileName){
        this.directory = directory;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdirs();
        }

        String filePath = directory + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public SymbolList readData() {
        SymbolList symbolList = new SymbolList();
        String filePath = directory + File.separator + fileName;
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);

            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].trim().equals("symbol")) {
                    Symbol symbol = new Symbol(
                            Integer.parseInt(data[1]), // symbol_id
                            Integer.parseInt(data[2]), // subsystem_id
                            data[3], // symbol_type
                            Integer.parseInt(data[4]), // usecase_id
                            data[4], // label
                            data[5] // description
                    );
                    symbolList.addSymbol(symbol);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (buffer != null) {
                    buffer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return symbolList;
    }

    @Override
    public void writeData(SymbolList symbolList) {
        //Import UseCaseSystemList from CSV
        UseCaseSystemListFileDataSource useCaseSystemListFileDataSource = new UseCaseSystemListFileDataSource(directory, fileName);
        UseCaseSystemList useCaseSystemList = useCaseSystemListFileDataSource.readData();
        //Import subsystemList from CSV
        SubsystemListFileDataSource subsystemListFileDataSource = new SubsystemListFileDataSource(directory, fileName);
        SubsystemList subsystemList = subsystemListFileDataSource.readData();
        //Import positionList from CSV
        PositionListFileDataSource positionListFileDataSource = new PositionListFileDataSource(directory, fileName);
        PositionList positionList = positionListFileDataSource.readData();
        //Import connectionList from CSV
        ConnectionListFileDataSource connectionListFileDataSource = new ConnectionListFileDataSource(directory, fileName);
        ConnectionList connectionList = connectionListFileDataSource.readData();

        //File writer
        String filePath = directory + File.separator + fileName;
        File file = new File(filePath);
        FileWriter writer = null;
        BufferedWriter buffer = null;
        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            //Write UseCaseSystemList to CSV
            for (UseCaseSystem useCaseSystem : useCaseSystemList.getSystemList()) {
                buffer.write(useCaseSystemListFileDataSource.createLine(useCaseSystem));
                buffer.newLine();
            }

            //Write SubsystemList to CSV
            for (Subsystem subsystem : subsystemList.getSubsystemList()) {
                buffer.write(subsystemListFileDataSource.createLine(subsystem));
                buffer.newLine();
            }

            //Write SymbolList to CSV
            for (Symbol symbol : symbolList.getSymbolList()) {
                buffer.write(createLine(symbol));
                buffer.newLine();
            }

            //Write PositionList to CSV
            for (Position position : positionList.getPositionList()) {
                buffer.write(positionListFileDataSource.createLine(position));
                buffer.newLine();
            }

            //Write ConnectionList to CSV
            for (Connection connection : connectionList.getConnectionList()) {
                buffer.write(connectionListFileDataSource.createLine(connection));
                buffer.newLine();
            }

            buffer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String createLine(Symbol symbol) {
        return "symbol" + ","
                + symbol.getSymbol_id() + ","
                + symbol.getSubsystem_id() + ","
                + symbol.getSymbol_type() + ","
                + symbol.getUsecase_id() + ","
                + symbol.getLabel() + ","
                + symbol.getDescription();
    }
}
