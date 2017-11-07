import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * transitc
 * Purpose: edit stopTimes from a stop
 *
 * @author: hoffmanjc
 * @version: 1.0 on 11/6/2017 at 9:05 PM
 */
public class DataView {

    private enum Mode {
        ROUTE, STOP, STOPTIME, TRIP
    }
    @FXML
    private VBox dataBox;
    @FXML
    protected SplitPane data1, data2, data3, data4, data5, data6, data7, data8;
    @FXML
    protected Button button1, button2, button3, button4, button5, button6, button7, button8;
    @FXML
    protected TextField field1, field2, field3, field4, field5, field6, field7, field8;

    private Object data;
    private Mode mode;

    public DataView() {
    }

    /**
     * Sets each data pane visible as a boolean
     * @param m1
     * @param m2
     * @param m3
     * @param m4
     * @param m5
     * @param m6
     * @param m7
     * @param m8
     * @return this
     * @author hoffmanjc
     */
    public DataView setVisibility(boolean m1, boolean m2, boolean m3, boolean m4, boolean m5, boolean m6, boolean m7, boolean m8) {
        data1.setVisible(m1);
        data2.setVisible(m2);
        data3.setVisible(m3);
        data4.setVisible(m4);
        data5.setVisible(m5);
        data6.setVisible(m6);
        data7.setVisible(m7);
        data8.setVisible(m8);
        return this;
    }

    /**
     * sets type of data
     * @param data
     * @return this
     * @author hoffmanjc
     */
    public DataView setData(Object data) {
        if(data instanceof StopTime) {
            mode = Mode.STOPTIME;
        } else if(data instanceof Route) {
            mode = Mode.ROUTE;
        } else if(data instanceof Trip) {
            mode = Mode.TRIP;
        } else if(data instanceof Stop) {
            mode = Mode.STOP;
        }
        this.data = data;
        return this;
    }

    /**
     * fills data in GUI
     * @return this
     * @author hoffmanjc
     */
    public DataView fillData() {
        if(mode == Mode.STOPTIME) {
            StopTime stopTime = (StopTime) data;
            //Trip ID, Stop ID, Arrival, Departure, Sequence, Stop Headsign, Pickup Type, Drop Off Type
            button1.setText("Trip ID");
            field1.setText(stopTime.getTripID());
            button2.setText("Stop ID");
            field2.setText(stopTime.getStopID());
            button3.setText("Arrival");
            field3.setText(stopTime.getArrivalTime().toString());
            button4.setText("Departure");
            field4.setText(stopTime.getDepartureTime().toString());
            button5.setText("Sequence");
            field5.setText(Integer.toString(stopTime.getStopSequence()));
            button6.setText("Stop Headsign");
            field6.setText(stopTime.getStopHeadsign());
            button7.setText("Pickup Type");
            field7.setText(stopTime.getPickupType());
            button8.setText("Drop Off Type");
            field8.setText(stopTime.getDropoffType());

        } else if(mode == Mode.ROUTE) {

        } else if(mode == Mode.TRIP) {

        } else if(mode == Mode.STOP) {

        }
        return this;
    }

    /**
     * gets GUI element
     * @return dataBox
     * @author hoffmanjc
     */
    public VBox getDataBox() {
        return this.dataBox;
    }

    /**
     * updates data pane 1
     * @author hoffmanjc
     */
    public void update1() {
        if(mode == Mode.STOPTIME) {
            StopTime stopTime = (StopTime) data;
            stopTime.setTripID(field1.getText());
        } else if(mode == Mode.ROUTE) {

        } else if(mode == Mode.TRIP) {

        } else if(mode == Mode.STOP) {

        }
    }

    /**
     * updates data pane 2
     * @author hoffmanjc
     */
    public void update2() {
        if(mode == Mode.STOPTIME) {
            //Trip ID, Stop ID, Arrival, Departure, Sequence, Stop Headsign, Pickup Type, Drop Off Type
            StopTime stopTime = (StopTime) data;
            stopTime.setStopID(field2.getText());
        } else if(mode == Mode.ROUTE) {

        } else if(mode == Mode.TRIP) {

        } else if(mode == Mode.STOP) {

        }
    }

    /**
     * updates data pane 3
     * @author hoffmanjc
     */
    public void update3() {
        if(mode == Mode.STOPTIME) {
            //Trip ID, Stop ID, Arrival, Departure, Sequence, Stop Headsign, Pickup Type, Drop Off Type
            StopTime stopTime = (StopTime) data;
            stopTime.setArrivalTime(new Time(field3.getText()));
        } else if(mode == Mode.ROUTE) {

        } else if(mode == Mode.TRIP) {

        } else if(mode == Mode.STOP) {

        }
    }

    /**
     * updates data pane 4
     * @author hoffmanjc
     */
    public void update4() {
        if(mode == Mode.STOPTIME) {
            //Trip ID, Stop ID, Arrival, Departure, Sequence, Stop Headsign, Pickup Type, Drop Off Type
            StopTime stopTime = (StopTime) data;
            stopTime.setArrivalTime(new Time(field4.getText()));
        } else if(mode == Mode.ROUTE) {

        } else if(mode == Mode.TRIP) {

        } else if(mode == Mode.STOP) {

        }
    }

    /**
     * updates data pane 5
     * @author hoffmanjc
     */
    public void update5() {
        if(mode == Mode.STOPTIME) {
            //Trip ID, Stop ID, Arrival, Departure, Sequence, Stop Headsign, Pickup Type, Drop Off Type
            StopTime stopTime = (StopTime) data;
            stopTime.setStopSequence(Integer.parseInt(field5.getText()));
        } else if(mode == Mode.ROUTE) {

        } else if(mode == Mode.TRIP) {

        } else if(mode == Mode.STOP) {

        }
    }

    /**
     * updates data pane 6
     * @author hoffmanjc
     */
    public void update6() {
        if(mode == Mode.STOPTIME) {
            //Trip ID, Stop ID, Arrival, Departure, Sequence, Stop Headsign, Pickup Type, Drop Off Type
            StopTime stopTime = (StopTime) data;
            stopTime.setStopHeadsign(field6.getText());
        } else if(mode == Mode.ROUTE) {

        } else if(mode == Mode.TRIP) {

        } else if(mode == Mode.STOP) {

        }
    }

    /**
     * updates data pane 7
     * @author hoffmanjc
     */
    public void update7() {
        if(mode == Mode.STOPTIME) {
            //Trip ID, Stop ID, Arrival, Departure, Sequence, Stop Headsign, Pickup Type, Drop Off Type
            StopTime stopTime = (StopTime) data;
            stopTime.setPickupType(field7.getText());
        } else if(mode == Mode.ROUTE) {

        } else if(mode == Mode.TRIP) {

        } else if(mode == Mode.STOP) {

        }
    }

    /**
     * updates data pane 8
     * @author hoffmanjc
     */
    public void update8() {
        if(mode == Mode.STOPTIME) {
            //Trip ID, Stop ID, Arrival, Departure, Sequence, Stop Headsign, Pickup Type, Drop Off Type
            StopTime stopTime = (StopTime) data;
            stopTime.setDropoffType(field8.getText());
        } else if(mode == Mode.ROUTE) {

        } else if(mode == Mode.TRIP) {

        } else if(mode == Mode.STOP) {

        }
    }

}
