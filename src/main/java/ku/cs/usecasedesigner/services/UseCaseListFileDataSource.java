package ku.cs.usecasedesigner.services;

import ku.cs.usecasedesigner.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class UseCaseListFileDataSource implements DataSource<UseCaseList>, ManageDataSource<UseCase>{
    private String directory;
    private String fileName;

    public UseCaseListFileDataSource(String directory, String fileName){
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
    public UseCaseList readData() {
        UseCaseList useCaseList = new UseCaseList();
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
                if (data[0].trim().equals("useCase")) {
                    UseCase useCase = new UseCase(
                            Integer.parseInt(data[1].trim()), // useCaseID
                            data[2].trim(), // useCaseName
                            Integer.parseInt(data[3].trim()), // actorID
                            data[4].trim(), // preCondition
                            data[5].trim(), // description
                            data[6].trim(), // postCondition
                            Integer.parseInt(data[7].trim()) // positionID
                    );
                    useCaseList.addUseCase(useCase);
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
        return useCaseList;
    }

    @Override
    public void writeData(UseCaseList useCaseList) {
        // Import ActorList from CSV
        ActorListFileDataSource actorListFileDataSource = new ActorListFileDataSource(directory, fileName);
        ActorList actorList = actorListFileDataSource.readData();
        //Import connectionList from CSV
        ConnectionListFileDataSource connectionListFileDataSource = new ConnectionListFileDataSource(directory, fileName);
        ConnectionList connectionList = connectionListFileDataSource.readData();
        //Import positionList from CSV
        PositionListFileDataSource positionListFileDataSource = new PositionListFileDataSource(directory, fileName);
        PositionList positionList = positionListFileDataSource.readData();
        // Import preferenceList from CSV
        PreferenceListFileDataSource preferenceListFileDataSource = new PreferenceListFileDataSource(directory, fileName);
        PreferenceList preferenceList = preferenceListFileDataSource.readData();
        //Import subsystemList from CSV
        SubsystemListFileDataSource subsystemListFileDataSource = new SubsystemListFileDataSource(directory, fileName);
        SubsystemList subsystemList = subsystemListFileDataSource.readData();
        //Import UseCaseSystemList from CSV
        UseCaseSystemListFileDataSource useCaseSystemListFileDataSource = new UseCaseSystemListFileDataSource(directory, fileName);
        UseCaseSystemList useCaseSystemList = useCaseSystemListFileDataSource.readData();

        //File writer
        String filePath = directory + File.separator + fileName;
        File file = new File(filePath);
        FileWriter writer = null;
        BufferedWriter buffer = null;
        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            // Write ActorList to CSV
            for (Actor actor : actorList.getActorList()) {
                buffer.write(actorListFileDataSource.createLine(actor));
                buffer.newLine();
            }

            //Write ConnectionList to CSV
            for (Connection connection : connectionList.getConnectionList()) {
                buffer.write(connectionListFileDataSource.createLine(connection));
                buffer.newLine();
            }

            //Write PositionList to CSV
            for (Position position : positionList.getPositionList()) {
                buffer.write(positionListFileDataSource.createLine(position));
                buffer.newLine();
            }

            //Write PreferenceList to CSV
            for (Preference preference : preferenceList.getPreferenceList()) {
                buffer.write(preferenceListFileDataSource.createLine(preference));
                buffer.newLine();
            }

            //Write SubsystemList to CSV
            for (Subsystem subsystem : subsystemList.getSubsystemList()) {
                buffer.write(subsystemListFileDataSource.createLine(subsystem));
                buffer.newLine();
            }

            //Write useCaseList to CSV
            for (UseCase useCase : useCaseList.getUseCaseList()) {
                buffer.write(createLine(useCase));
                buffer.newLine();
            }

            //Write UseCaseSystemList to CSV
            for (UseCaseSystem useCaseSystem : useCaseSystemList.getSystemList()) {
                buffer.write(useCaseSystemListFileDataSource.createLine(useCaseSystem));
                buffer.newLine();
            }

            buffer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String createLine(UseCase useCase) {
        return "useCase" + ","
                + useCase.getUseCaseID() + ","
                + useCase.getUseCaseName() + ","
                + useCase.getActorID() + ","
                + useCase.getPreCondition() + ","
                + useCase.getDescription() + ","
                + useCase.getPostCondition() + ","
                + useCase.getPositionID();
    }
}
