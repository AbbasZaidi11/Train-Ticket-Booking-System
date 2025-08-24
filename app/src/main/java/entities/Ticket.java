package entities;

import java.util.Date;

public class Ticket {
    private String ticketId;
    private String userId;
    private String source;
    private String destination;
    private Date dateOfTravel;
    private Train train;

    // seat location to support cancel->free seat
    private int row;
    private int col;
    public Ticket() {}

    public Ticket(String ticketId, String userId, String source, String destination,
                  Date dateOfTravel, Train train, int row, int col) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.train = train;
        this.row = row;
        this.col = col;
    }

    public String getTicketInfo() {
        return String.format(
                "Ticket ID: %s | User %s | %s -> %s on %s | Train %s (%d) | Seat [%d,%d]",
                ticketId, userId, source, destination, dateOfTravel,
                train != null ? train.getTrainId() : "?",
                train != null ? train.getTrainNo() : -1,
                row, col
        );
    }

    public String getTicketId() { return ticketId; }
    public String getUserId() { return userId; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public Date getDateOfTravel() { return dateOfTravel; }
    public Train getTrain() { return train; }
    public int getRow() { return row; }
    public int getCol() { return col; }

    public void setTicketId(String ticketId) { this.ticketId = ticketId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setSource(String source) { this.source = source; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setDateOfTravel(Date dateOfTravel) { this.dateOfTravel = dateOfTravel; }
    public void setTrain(Train train) { this.train = train; }
    public void setRow(int row) { this.row = row; }
    public void setCol(int col) { this.col = col; }
}
