package entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Train {
    @JsonProperty("train_id")
    private String trainId;

    @JsonProperty("train_no")
    private int trainNo; // numeric in JSON

    private List<List<Integer>> seats;

    @JsonProperty("station_times")
    @JsonFormat(shape = JsonFormat.Shape.OBJECT, pattern = "HH:mm:ss")
    private Map<String, Time> stationTime;

    private List<String> stations;

    public Train() {}

    public Train(String trainId, int trainNo, List<List<Integer>> seats,
                 Map<String, Time> stationTime, List<String> stations) {
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seats = seats != null ? seats : new ArrayList<>();
        this.stationTime = stationTime != null ? stationTime : new HashMap<>();
        this.stations = stations != null ? stations : new ArrayList<>();
    }

    public String getTrainId() { return trainId; }
    public void setTrainId(String trainId) { this.trainId = trainId; }

    public int getTrainNo() { return trainNo; }
    public void setTrainNo(int trainNo) { this.trainNo = trainNo; }

    public List<List<Integer>> getSeats() { return seats; }
    public void setSeats(List<List<Integer>> seats) { this.seats = seats != null ? seats : new ArrayList<>(); }

    public Map<String, Time> getStationTime() { return stationTime; }
    public void setStationTime(Map<String, Time> stationTime) { this.stationTime = stationTime != null ? stationTime : new HashMap<>(); }

    public List<String> getStations() { return stations; }
    public void setStations(List<String> stations) { this.stations = stations != null ? stations : new ArrayList<>(); }

    public String getTrainInfo() {
        return String.format("Train ID: %s Train No: %d", trainId, trainNo);
    }
}
