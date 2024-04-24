package ku.cs.usecasedesigner.services;

import ku.cs.usecasedesigner.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class UseCaseSystemListFileDataSource implements DataSource<UseCaseSystemList>, ManageDataSource<UseCaseSystem>{
    private String directory;
    private String fileName;

    public UseCaseSystemListFileDataSource(String directory, String fileName) {
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
    public UseCaseSystemList readData() {
        UseCaseSystemList useCaseSystemList = new UseCaseSystemList();
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
                if (data[0].trim().equals("system")) {
                    UseCaseSystem useCaseSystem = new UseCaseSystem(
                            Double.parseDouble(data[1]), // system_id
                            data[2] // system_name
                    );
                    useCaseSystemList.addSystem(useCaseSystem);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (buffer != null) {
                    buffer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return useCaseSystemList;
    }

    @Override
    public void writeData(UseCaseSystemList useCaseSystemList) {
        //Import subsystemList from CSV
        SubsystemListFileDataSource subsystemListFileDataSource = new SubsystemListFileDataSource(directory, fileName);
        SubsystemList subsystemList = subsystemListFileDataSource.readData();
        //Import symbolList from CSV
        SymbolListFileDataSource symbolListFileDataSource = new SymbolListFileDataSource(directory, fileName);
        SymbolList symbolList = symbolListFileDataSource.readData();
        //Import positionList from CSV
        PositionListFileDataSource positionListFileDataSource = new PositionListFileDataSource(directory, fileName);
        PositionList positionList = positionListFileDataSource.readData();

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
                String line = createLine(useCaseSystem);
                buffer.append(line);
                buffer.newLine();
            }

            //Write SubsystemList to CSV
            for (Subsystem subsystem : subsystemList.getSubsystemList()) {
                String line = subsystemListFileDataSource.createLine(subsystem);
                buffer.append(line);
                buffer.newLine();
            }

            //Write SymbolList to CSV
            for (Symbol symbol : symbolList.getSymbolList()) {
                String line = symbolListFileDataSource.createLine(symbol);
                buffer.append(line);
                buffer.newLine();
            }

            //Write PositionList to CSV
            for (Position position : positionList.getPositionList()) {
                String line = positionListFileDataSource.createLine(position);
                buffer.append(line);
                buffer.newLine();
            }


            buffer.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String createLine(UseCaseSystem useCaseSystem) {
        return "system,"
                + useCaseSystem.getSystem_id() + ","
                + useCaseSystem.getSystem_name();
    }
}
