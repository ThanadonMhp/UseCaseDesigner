package ku.cs.usecasedesigner.services;

import ku.cs.usecasedesigner.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class UseCaseDetailListFileDataSource implements DataSource<UseCaseDetailList>, ManageDataSource<UseCaseDetail>{
    private String directory;
    private String fileName;

    public UseCaseDetailListFileDataSource(String directory, String fileName) {
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
    public UseCaseDetailList readData() {
        UseCaseDetailList useCaseDetailList = new UseCaseDetailList();
        String filePath = directory + File.separator + fileName;
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].trim().equals("useCaseDetail")) {
                    UseCaseDetail useCaseDetail = new UseCaseDetail(
                            Integer.parseInt(data[1]), // useCaseID
                            data[2], // type
                            Integer.parseInt(data[3]), // number
                            data[4] // detail
                    );
                    useCaseDetailList.addUseCaseDetail(useCaseDetail);
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

        return useCaseDetailList;
    }

    @Override
    public void writeData(UseCaseDetailList useCaseDetailList) {
        // Import actorList from CSV
        ActorListFileDataSource actorListFileDataSource = new ActorListFileDataSource(directory, fileName);
        ActorList actorList = actorListFileDataSource.readData();
        // Import componentPreferenceList from CSV
        ComponentPreferenceListFileDataSource componentPreferenceListFileDataSource = new ComponentPreferenceListFileDataSource(directory, fileName);
        ComponentPreferenceList componentPreferenceList = componentPreferenceListFileDataSource.readData();
        // Import connectionList from CSV
        ConnectionListFileDataSource connectionListFileDataSource = new ConnectionListFileDataSource(directory, fileName);
        ConnectionList connectionList = connectionListFileDataSource.readData();
        // Import positionList from CSV
        PositionListFileDataSource positionListFileDataSource = new PositionListFileDataSource(directory, fileName);
        PositionList positionList = positionListFileDataSource.readData();
        // Import preferenceList from CSV
        PreferenceListFileDataSource preferenceListFileDataSource = new PreferenceListFileDataSource(directory, fileName);
        PreferenceList preferenceList = preferenceListFileDataSource.readData();
        // Import subSystemList from CSV
        SubSystemListFileDataSource subSystemListFileDataSource = new SubSystemListFileDataSource(directory, fileName);
        SubSystemList subSystemList = subSystemListFileDataSource.readData();
        // Import useCaseList from CSV
        UseCaseListFileDataSource useCaseListFileDataSource = new UseCaseListFileDataSource(directory, fileName);
        UseCaseList useCaseList = useCaseListFileDataSource.readData();
        // Import useCaseSystemList from CSV
        UseCaseSystemListFileDataSource useCaseSystemListFileDataSource = new UseCaseSystemListFileDataSource(directory, fileName);
        UseCaseSystemList useCaseSystemList = useCaseSystemListFileDataSource.readData();

        // File writer
        String filePath = directory + File.separator + fileName;
        File file = new File(filePath);
        FileWriter writer = null;
        BufferedWriter buffer = null;
        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);

            // Write ActorList to CSV
            for (Actor actor : actorList.getActorList()) {
                String line = actorListFileDataSource.createLine(actor);
                buffer.append(line);
                buffer.newLine();
            }

            // Write ComponentPreferenceList to CSV
            for (ComponentPreference componentPreference : componentPreferenceList.getComponentPreferenceList()) {
                String line = componentPreferenceListFileDataSource.createLine(componentPreference);
                buffer.append(line);
                buffer.newLine();
            }

            // Write ConnectionList to CSV
            for (Connection connection : connectionList.getConnectionList()) {
                String line = connectionListFileDataSource.createLine(connection);
                buffer.append(line);
                buffer.newLine();
            }

            // Write PositionList to CSV
            for (Position position : positionList.getPositionList()) {
                String line = positionListFileDataSource.createLine(position);
                buffer.append(line);
                buffer.newLine();
            }

            // Write PreferenceList to CSV
            for (Preference preference : preferenceList.getPreferenceList()) {
                String line = preferenceListFileDataSource.createLine(preference);
                buffer.append(line);
                buffer.newLine();
            }

            // Write SubSystemList to CSV
            for (SubSystem subSystem : subSystemList.getSubSystemList()) {
                String line = subSystemListFileDataSource.createLine(subSystem);
                buffer.append(line);
                buffer.newLine();
            }

            // Write UseCaseDetailList to CSV
            for (UseCaseDetail useCaseDetail : useCaseDetailList.getUseCaseDetailList()) {
                buffer.write(createLine(useCaseDetail));
                buffer.newLine();
            }

            // Write UseCaseList to CSV
            for (UseCase useCase : useCaseList.getUseCaseList()) {
                String line = useCaseListFileDataSource.createLine(useCase);
                buffer.append(line);
                buffer.newLine();
            }

            // Write UseCaseSystemList to CSV
            for (UseCaseSystem useCaseSystem : useCaseSystemList.getSystemList()) {
                String line = useCaseSystemListFileDataSource.createLine(useCaseSystem);
                buffer.append(line);
                buffer.newLine();
            }

            buffer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String createLine(UseCaseDetail useCaseDetail) {
        return "useCaseDetail" + ","
                + useCaseDetail.getUseCaseID() + ","
                + useCaseDetail.getType() + ","
                + useCaseDetail.getNumber() + ","
                + useCaseDetail.getDetail();
    }
}
