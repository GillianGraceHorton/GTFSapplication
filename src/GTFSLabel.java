import javafx.scene.control.Label;

public class GTFSLabel extends Label{

    private Object item;
    private Boolean stopTime;

    public GTFSLabel(Object item){
        this.item = item;
        this.setVisible(true);
        this.setWrapText(true);
        stopTime = false;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public Boolean isStopTime() {
        return stopTime;
    }

    public void setStopTime(Boolean stopTime) {
        this.stopTime = stopTime;
    }
}
