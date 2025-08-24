package entities.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainService {
    private List<Train> trainList;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // Use a relative path for portability
    private static final String TRAINS_PATH = "src/main/java/localDB/trains.json";

    public TrainService() throws IOException {
        this.trainList = loadTrains();
    }

    public List<Train> loadTrains() throws IOException {
        File trainsFile = new File(TRAINS_PATH);

        if (!trainsFile.exists() || trainsFile.length() == 0) {
            System.out.println("Trains file not found or empty. Creating new one...");
            trainsFile.getParentFile().mkdirs();
            List<Train> emptyList = new ArrayList<>();
            OBJECT_MAPPER.writeValue(trainsFile, emptyList);
            return emptyList;
        }

        try {
            return OBJECT_MAPPER.readValue(trainsFile, new TypeReference<List<Train>>() {});
        } catch (Exception e) {
            System.out.println("Error reading trains file, creating fresh: " + e.getMessage());
            List<Train> emptyList = new ArrayList<>();
            OBJECT_MAPPER.writeValue(trainsFile, emptyList);
            return emptyList;
        }
    }

    public void saveTrains() throws IOException {
        File trainsFile = new File(TRAINS_PATH);
        OBJECT_MAPPER.writeValue(trainsFile, trainList);
    }

    public List<Train> searchTrains(String source, String destination) {
        return trainList
                .stream()
                .filter(train -> validTrain(train, source, destination))
                .toList();
    }

    public boolean validTrain(Train train, String source, String destination) {
        List<String> stationOrder = train.getStations();
        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());
        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }

    public List<Train> getTrainList() {
        return trainList;
    }

    public Train findById(String trainId) {
        return trainList.stream()
                .filter(t -> t.getTrainId().equalsIgnoreCase(trainId))
                .findFirst()
                .orElse(null);
    }
}
