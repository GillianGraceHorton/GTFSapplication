/**
 * Author:
 * Description:
 * Date Created: 10/3/2017
 */
public class StopTime {

    private String trip_id;
    private Time arrival_time;
    private Time departure_time;

    private Stop stop;
    private String stop_id;
    private int stop_sequence;
    private String stop_headsign;
    private String pickup_type;
    private String dropoff_type;

    /**
     * Author:
     * Description:
     *
     * @param trip_id
     * @param arrival_time
     * @param departure_time
     * @param stop_id
     * @param stop_sequence
     * @param stop_headsign
     * @param pickup_type
     * @param drop_off_type
     */

    public StopTime(String trip_id, String arrival_time, String departure_time, String stop_id,
                    String stop_sequence, String stop_headsign, String pickup_type, String drop_off_type) {

        this.trip_id = trip_id;
        this.arrival_time = new Time(arrival_time);
        this.departure_time = new Time(departure_time);
        if (this.arrival_time.getTime() > this.departure_time.getTime()) {
            throw new IllegalArgumentException("the arrival time must be less than or equal to the departure time");
        }
        this.stop_id = stop_id;
        this.stop_sequence = Integer.parseInt(stop_sequence);
        this.stop_headsign = stop_headsign;
        this.pickup_type = pickup_type;
        this.dropoff_type = drop_off_type;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public Stop getStop() {
        return stop;
    }

    /**
     * Author:
     * Description:
     *
     * @param stop
     */
    public void setStop(Stop stop) {
        this.stop = stop;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public String getTripID() {
        return trip_id;
    }

    /**
     * Author:
     * Description:
     *
     * @param trip_id
     */
    public void setTripID(String trip_id) {
        this.trip_id = trip_id;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public Time getArrivalTime() {
        return arrival_time;
    }

    /**
     * Author:
     * Description:
     *
     * @param arrival_time
     */
    public void setArrivalTime(Time arrival_time) {
        this.arrival_time = arrival_time;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public Time getDepartureTime() {
        return departure_time;
    }

    /**
     * Author:
     * Description:
     *
     * @param departure_time
     */
    public void setDepartureTime(Time departure_time) {
        this.departure_time = departure_time;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public String getStopID() {
        return stop_id;
    }

    /**
     * Author:
     * Description:
     *
     * @param stop_id
     */
    public void setStopID(String stop_id) {
        this.stop_id = stop_id;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public int getStopSequence() {
        return stop_sequence;
    }

    /**
     * Author:
     * Description:
     *
     * @param stop_sequence
     */
    public void setStopSequence(int stop_sequence) {
        this.stop_sequence = stop_sequence;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public String getStopHeadsign() {
        return stop_headsign;
    }

    /**
     * Author:
     * Description:
     *
     * @param stop_headsign
     */
    public void setStopHeadsign(String stop_headsign) {
        this.stop_headsign = stop_headsign;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public String getPickupType() {
        return pickup_type;
    }

    /**
     * Author:
     * Description:
     *
     * @param pickup_type
     */
    public void setPickupType(String pickup_type) {
        this.pickup_type = pickup_type;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public String getDropoffType() {
        return dropoff_type;
    }

    /**
     * Author:
     * Description:
     *
     * @param dropoff_type
     */
    public void setDropoffType(String dropoff_type) {
        this.dropoff_type = dropoff_type;
    }

    /**
     * Author: Joseph Heinz - heinzja@msoee.edu
     * Description: Returns formatted String used for GUI
     *
     * @return String - formatted string used for GUI
     */
    public String toString() {
        return "TripID: " + getTripID() + "\n" +
                "StopID: " + getStopID() + "\n" +
                "Stop Sequence: " + getStopSequence() + "\n" +
                "Stop Headsign: " + getStopHeadsign() + "\n" +
                "Pickup Type: " + getPickupType() + "\n" +
                "Drop Off Type: " + getDropoffType() + "\n" +
                "Arrival Time: " + getArrivalTime() + "\n" +
                "Departure Time: " + getDepartureTime() + "\n";
    }

    /**
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: Returns formatted string used for StopTimes file exporting
     *
     * @return String - formatted string used for export
     */
    public String toStringExport() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s", getTripID(), getStopID(), getStopSequence(), getStopHeadsign(), getPickupType(), getDropoffType(), getArrivalTime(), getDepartureTime());
    }
}
