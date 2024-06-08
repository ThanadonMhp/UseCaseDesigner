package ku.cs.usecasedesigner.services;

import ku.cs.usecasedesigner.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ActorListFileDataSource implements DataSource<ActorList>, ManageDataSource<Actor>{
    private String directory;
    private String fileName;

    public ActorListFileDataSource(String directory, String fileName) {
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
    public ActorList readData() {
        ActorList actorList = new ActorList();
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
                if (data[0].trim().equals("actor")) {
                    Actor actor = new Actor(
                            Integer.parseInt(data[1].trim()), // actorID
                            data[2].trim(), // actorName
                            data[3].trim(), // alias
                            data[4].trim(), // description
                            data[5].trim(), // note
                            Integer.parseInt(data[6].trim()) // positionID
                    );
                    actorList.addActor(actor);
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

        return actorList;
    }

    @Override
    public void writeData(ActorList actorList) {
        // Import componentPreferenceList from CSV
        ComponentPreferenceListFileDataSource componentPreferenceListFileDataSource = new ComponentPreferenceListFileDataSource(directory, fileName);
        ComponentPreferenceList componentPreferenceList = componentPreferenceListFileDataSource.readData();
        // Import connectionList from CSV
        ConnectionListFileDataSource connectionListFileDataSource = new ConnectionListFileDataSource(directory, fileName);
        ConnectionList connectionList = connectionListFileDataSource.readData();
        // Import noteList from CSV
        NoteListFileDataSource noteListFileDataSource = new NoteListFileDataSource(directory, fileName);
        NoteList noteList = noteListFileDataSource.readData();
        // Import positionList from CSV
        PositionListFileDataSource positionListFileDataSource = new PositionListFileDataSource(directory, fileName);
        PositionList positionList = positionListFileDataSource.readData();
        // Import preferenceList from CSV
        PreferenceListFileDataSource preferenceListFileDataSource = new PreferenceListFileDataSource(directory, fileName);
        PreferenceList preferenceList = preferenceListFileDataSource.readData();
        // Import subSystemList from CSV
        SubSystemListFileDataSource subsystemListFileDataSource = new SubSystemListFileDataSource(directory, fileName);
        SubSystemList subsystemList = subsystemListFileDataSource.readData();
        // Import useCaseDetailList from CSV
        UseCaseDetailListFileDataSource useCaseDetailListFileDataSource = new UseCaseDetailListFileDataSource(directory, fileName);
        UseCaseDetailList useCaseDetailList = useCaseDetailListFileDataSource.readData();
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

            // Write actorList to file
            for (Actor actor : actorList.getActorList()) {
                String line = createLine(actor);
                buffer.append(line);
                buffer.newLine();
            }

            // Write componentPreferenceList to file
            for (ComponentPreference componentPreference : componentPreferenceList.getComponentPreferenceList()) {
                String line = componentPreferenceListFileDataSource.createLine(componentPreference);
                buffer.append(line);
                buffer.newLine();
            }

            // Write connectionList to file
            for (Connection connection : connectionList.getConnectionList()) {
                String line = connectionListFileDataSource.createLine(connection);
                buffer.append(line);
                buffer.newLine();
            }

            // Write noteList to file
            for (Note note : noteList.getNoteList()) {
                String line = noteListFileDataSource.createLine(note);
                buffer.append(line);
                buffer.newLine();
            }

            // Write positionList to file
            for (Position position : positionList.getPositionList()) {
                String line = positionListFileDataSource.createLine(position);
                buffer.append(line);
                buffer.newLine();
            }

            // Write preferenceList to file
            for (Preference preference : preferenceList.getPreferenceList()) {
                String line = preferenceListFileDataSource.createLine(preference);
                buffer.append(line);
                buffer.newLine();
            }

            // Write subSystemList to file
            for (SubSystem subsystem : subsystemList.getSubSystemList()) {
                String line = subsystemListFileDataSource.createLine(subsystem);
                buffer.append(line);
                buffer.newLine();
            }

            // Write useCaseDetailList to file
            for (UseCaseDetail useCaseDetail : useCaseDetailList.getUseCaseDetailList()) {
                String line = useCaseDetailListFileDataSource.createLine(useCaseDetail);
                buffer.append(line);
                buffer.newLine();
            }

            // Write useCaseList to file
            for (UseCase useCase : useCaseList.getUseCaseList()) {
                String line = useCaseListFileDataSource.createLine(useCase);
                buffer.append(line);
                buffer.newLine();
            }

            // Write useCaseSystemList to file
            for (UseCaseSystem useCaseSystem : useCaseSystemList.getSystemList()) {
                String line = useCaseSystemListFileDataSource.createLine(useCaseSystem);
                buffer.append(line);
                buffer.newLine();
            }

            buffer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String createLine(Actor actor) {
        return "actor,"
                + actor.getActorID() + ","
                + actor.getActorName() + ","
                + actor.getAlias() + ","
                + actor.getDescription() + ","
                + actor.getNote() + ","
                + actor.getPositionID();
    }
}
