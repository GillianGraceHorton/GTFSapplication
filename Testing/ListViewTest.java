import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ListViewTest {
    ListView listView;

    @BeforeEach
    public void setUp(){
        listView = new ListView();
    }

    /**
     * @author: hortong
     */
    @Test
    void update() {
        ArrayList<Object> updates = new ArrayList<>();
        Stop stop = new Stop(0, 0, "test",  "1A", "");
        DataStorage dataStorage = new DataStorage();
        dataStorage.attach(listView);

        dataStorage.notifyObservers(updates);
        assertEquals(listView.getTabs().get(0).getContent().getAccessibleText(), "");

        updates.add(stop);
        assertEquals(listView.getTabs().get(0).getContent().getAccessibleText(), stop.toString());
    }

}