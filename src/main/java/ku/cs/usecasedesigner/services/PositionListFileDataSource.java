package ku.cs.usecasedesigner.services;

import ku.cs.usecasedesigner.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class PositionListFileDataSource implements DataSource<PositionList>, ManageDataSource<Position>{
    private String directory;
    private String fileName;

    public PositionListFileDataSource(String directory, String fileName) {
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
    public PositionList readData() {
        PositionList positionList = new PositionList();
        String filePath = directory + File.separator + fileName;
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);

            String line = "";
            while ((line = buffer.readLine()) != null)
            {
                String[] data = line.split(",");
                if (data[0].trim().equals("position")) {
                    Position position = new Position(
                            Double.parseDouble(data[1]), // position_id
                            Double.parseDouble(data[2]), // symbol_id
                            Double.parseDouble(data[3]), // x_position
                            Double.parseDouble(data[4]), // y_position
                            Double.parseDouble(data[5]), // fit_width
                            Double.parseDouble(data[6]), // fit_height
                            Double.parseDouble(data[7])  // rotation
                    );
                    positionList.addPosition(position);
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
        return positionList;
    }

    @Override
    public void writeData(PositionList positionList) {
        //Import UseCaseSystemList from CSV
        UseCaseSystemListFileDataSource useCaseSystemListFileDataSource = new UseCaseSystemListFileDataSource(directory, fileName);
        UseCaseSystemList useCaseSystemList = useCaseSystemListFileDataSource.readData();
        //Import subsystemList from CSV
        SubsystemListFileDataSource subsystemListFileDataSource = new SubsystemListFileDataSource(directory, fileName);
        SubsystemList subsystemList = subsystemListFileDataSource.readData();
        //Import symbolList from CSV
        SymbolListFileDataSource symbolListFileDataSource = new SymbolListFileDataSource(directory, fileName);
        SymbolList symbolList = symbolListFileDataSource.readData();

        //File writer
        String filePath = directory + File.separator + fileName;
        File file = new File(filePath);
        FileWriter writer = null;
        BufferedWriter buffer = null;
        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            //Write UseCaseSystemList to CSV
            for (UseCaseSystem useCaseSystem :useCaseSystemList.getSystemList()){
                String line = useCaseSystemListFileDataSource.createLine(useCaseSystem);
                buffer.append(line);
                buffer.newLine();
            }

            //Write SubSystemList to CSV
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
                String line = createLine(position);
                buffer.append(line);
                buffer.newLine();
            }


            buffer.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String createLine(Position position) {
        return "position" + ","
                + position.getPosition_id() + ","
                + position.getSymbol_id() + ","
                + position.getX_position() + ","
                + position.getY_position() + ","
                + position.getFit_width() + ","
                + position.getFit_height() + ","
                + position.getRotation();
    }
}
