package entities.services;

import entities.Ticket;
import entities.Train;
import entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UserBookingService {

    private User user;
    private List<User> userList;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    // Relative path for portability
    private static final String USERS_PATH = "src/main/java/localDB/users.json";

    public UserBookingService() throws IOException {
        this.userList = loadUsers();
    }

    public UserBookingService(User user) throws IOException {
        this.userList = loadUsers();
        this.user = user;
    }

    public void setCurrentUser(User user) {
        this.user = user;
    }

    public User getCurrentUser() {
        return this.user;
    }

    public List<User> loadUsers() throws IOException {
        File usersFile = new File(USERS_PATH);

        if (!usersFile.exists() || usersFile.length() == 0) {
            usersFile.getParentFile().mkdirs();
            List<User> emptyList = new ArrayList<>();
            OBJECT_MAPPER.writeValue(usersFile, emptyList);
            return emptyList;
        }

        try {
            return OBJECT_MAPPER.readValue(usersFile, new TypeReference<List<User>>() {});
        } catch (Exception e) {
            System.out.println("Corrupted users file, creating fresh: " + e.getMessage());
            List<User> emptyList = new ArrayList<>();
            OBJECT_MAPPER.writeValue(usersFile, emptyList);
            return emptyList;
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        OBJECT_MAPPER.writeValue(usersFile, userList);
    }

    /** Return matched user if login succeeds */
    public Optional<User> loginUser(User attempt) {
        return userList.stream()
                .filter(u ->
                        attempt.getName().equals(u.getName()) &&
                                UserServiceUtil.checkPassword(attempt.getPassword(), u.getHashPassword())
                ).findFirst();
    }

    public Boolean signUp(User user1) {
        try {
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }

    public void fetchBooking() {
        if (user == null) {
            System.out.println("No user logged in.");
            return;
        }
        user.printTickets();
    }

    public boolean cancelBooking(String ticketId) {
        if (user == null) return false;

        try {
            List<Ticket> arr = user.getTicketsBooked();
            Optional<Ticket> toRemove = arr.stream()
                    .filter(tix -> tix.getTicketId().equalsIgnoreCase(ticketId))
                    .findFirst();

            if (toRemove.isEmpty()) return false;

            // Free the seat in train seats and persist
            Ticket t = toRemove.get();
            TrainService trainService = new TrainService();
            Train target = trainService.findById(t.getTrain().getTrainId());
            if (target != null) {
                List<List<Integer>> seats = target.getSeats();
                int r = t.getRow();
                int c = t.getCol();
                if (r >= 0 && r < seats.size() && c >= 0 && c < seats.get(r).size()) {
                    seats.get(r).set(c, 0);
                    trainService.saveTrains();
                }
            }

            arr.remove(toRemove.get());
            saveUserListToFile();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public List<Train> getTrains(String source, String destination) {
        try {
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(Train trainSelectedForBooking) {
        return trainSelectedForBooking.getSeats();
    }

    /** Books seat + persists updated trains.json */
    public Boolean bookTrainSeat(Train trainSelectedForBooking, int row, int col) {
        try {
            TrainService trainService = new TrainService();
            Train liveTrain = trainService.findById(trainSelectedForBooking.getTrainId());
            if (liveTrain == null) return false;

            List<List<Integer>> vec = liveTrain.getSeats();
            if (row < 0 || row >= vec.size() || col < 0 || col >= vec.get(row).size()) {
                return false;
            }
            int element = vec.get(row).get(col);
            if (element == 1) return false;

            vec.get(row).set(col, 1);
            trainService.saveTrains(); // persist seat change
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /** Persist ticket into current user and save users.json */
    public boolean addTicketToCurrentUser(Ticket t) {
        if (user == null) return false;
        try {
            if (user.getTicketsBooked() == null) user.setTicketsBooked(new ArrayList<>());
            user.getTicketsBooked().add(t);

            // also update the user in userList (replace by userId)
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUserId().equals(user.getUserId())) {
                    userList.set(i, user);
                    break;
                }
            }
            saveUserListToFile();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
