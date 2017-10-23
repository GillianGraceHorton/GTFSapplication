import java.security.InvalidParameterException;

public class StopTime {

    private String trip_id;
    private Time arrival_time;
    private Time departure_time;
    private String stop_id;
    private String stop_sequence;
    private String stop_headsign;
    private String pickup_type;
    private String dropoff_type;

    public StopTime(String trip_id, String arrival_time, String departure_time, String stop_id,
                    String stop_sequence, String stop_headsign, String pickup_type,
                    String drop_off_type){

        this.trip_id = trip_id;
        this.arrival_time = new Time(arrival_time);
        this.departure_time = new Time(departure_time);
        if(this.arrival_time.compareTo(this.departure_time) == 1){
            throw new IllegalArgumentException("the arrival time must be less than or equal to " +
                    "the departure time");
        }
        this.stop_id = stop_id;
        this.stop_sequence = stop_sequence;
        this.stop_headsign = stop_headsign;
        this.pickup_type = pickup_type;
        this.dropoff_type = drop_off_type;
    }

    public String getTripID() {
        return trip_id;
    }

    public Time getArrivalTime() {
        return arrival_time;
    }

    public Time getDepartureTime() {
        return departure_time;
    }

    public String getStopID() {
        return stop_id;
    }

    public String getStopSequence() {
        return stop_sequence;
    }

    public String getStopHeadsign() {
        return stop_headsign;
    }

    public String getPickupType() {
        return pickup_type;
    }

    public String getDropoffType() {
        return dropoff_type;
    }

    public void setTripID(String trip_id) {
        this.trip_id = trip_id;
    }

    public void setArrivalTime(Time arrival_time) {
        this.arrival_time = arrival_time;
    }

    public void setDepartureTime(Time departure_time) {
        this.departure_time = departure_time;
    }

    public void setStopID(String stop_id) {
        this.stop_id = stop_id;
    }

    public void setStopSequence(String stop_sequence) {
        this.stop_sequence = stop_sequence;
    }

    public void setStopHeadsign(String stop_headsign) {
        this.stop_headsign = stop_headsign;
    }

    public void setPickupType(String pickup_type) {
        this.pickup_type = pickup_type;
    }

    public void setDropoffType(String dropoff_type) {
        this.dropoff_type = dropoff_type;
    }

    public String toString(){
        return  "TripID: " + getTripID() + "\n" +
                "StopID: " + getStopID() + "\n" +
                "Stop Sequence: " + getStopSequence() + "\n" +
                "Stop Headsign: " + getStopHeadsign() + "\n" +
                "Pickup Type: " + getPickupType() + "\n" +
                "Drop Off Type: " + getDropoffType() + "\n" +
                "Arrival Time: " + getArrivalTime() + "\n" +
                "Departure Time: " + getDepartureTime() + "\n";
    }
}
