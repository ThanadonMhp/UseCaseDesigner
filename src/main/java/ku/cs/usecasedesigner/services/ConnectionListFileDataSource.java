package ku.cs.usecasedesigner.services;

import ku.cs.usecasedesigner.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ConnectionListFileDataSource implements DataSource<ConnectionList>, ManageDataSource<Connection>{
    private String directory;
    private String fileName;

    public ConnectionListFileDataSource(String directory, String fileName) {
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
    public ConnectionList readData() {
        ConnectionList connectionList = new ConnectionList();
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
                if (data[0].trim().equals("connection")) {
                    Connection connection = new Connection(
                            Double.parseDouble(data[1]), // startX
                            Double.parseDouble(data[2]), // startY
                            Double.parseDouble(data[3]), // endX
                            Double.parseDouble(data[4])  // endY
                    );
                    connectionList.addConnection(connection);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
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
        return connectionList;
    }

    @Override
    public void writeData(ConnectionList connectionList) {
        //Import useCaseSystemList from CSV
        UseCaseSystemListFileDataSource useCaseSystemListFileDataSource = new UseCaseSystemListFileDataSource(directory, fileName);
        UseCaseSystemList useCaseSystemList = useCaseSystemListFileDataSource.readData();
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
                String line  = useCaseSystemListFileDataSource.createLine(useCaseSystem);
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

            //Write ConnectionList to CSV
            for (Connection connection : connectionList.getConnectionList()) {
                String line = createLine(connection);
                buffer.append(line);
                buffer.newLine();
            }

            buffer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String createLine(Connection connection) {
        return "connection,"
                + connection.getStartX() + ","
                + connection.getStartY() + ","
                + connection.getEndX() + ","
                + connection.getEndY();
    }
}
