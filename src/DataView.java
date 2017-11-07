import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * transitc
 * Purpose:
 *
 * @author: hoffmanjc
 * @version: 1.0 on 11/6/2017 at 9:05 PM
 */
public class DataView {

    @FXML
    private VBox dataBox;
    @FXML
    protected SplitPane data1, data2, data3, data4, data5, data6, data7, data8;
    @FXML
    protected Button button1, button2, button3, button4, button5, button6, button7, button8;
    @FXML
    protected TextField field1, field2, field3, field4, field5, field6, field7, field8;

    private Object data;

    public DataView() {
    }

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

    public DataView setData(Object data) {
        this.data = data;
        return this;
    }

    public DataView fillData() {
        if(data instanceof StopTime) {
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

        } else if(data instanceof Route) {

        } else if(data instanceof Trip) {

        } else if(data instanceof Stop) {

        }
        return this;
    }

    public VBox getDataBox() {
        return this.dataBox;
    }

}
