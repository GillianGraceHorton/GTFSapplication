import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TripTest {
    private Trip trip;
    private Route route;
    @BeforeEach
    void startUp(){
        trip = new Trip("TEST_TRIPID","TEST_SERVICEID","TEST_TRIPID","TEST_TRIPHEADSIGN","TEST_DIRECTIONID","TEST_BLOCKID","TEST_SHAPEID");
        route = new Route("TEST_ROUTEID","TEST_AGENCYID","TEST_RTSHORTNAME",
                "TEST_RTLONGNAME","TEST_ROUTEDESC","TEST_ROUTETYPE","TEST_ROUTEURL","TEST_ROUTECOLOR","TEST_ROUTETEXTCOLOR");
    }

    /**
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: tests addStop method functionality
     */
    @Test
    void addStop() {
        //TestCase1: checks to see if addStop can add valid stop
        assertTrue(trip.addStop(new Stop(0,0,"TEST_STOPNAME","TEST_STOPID","TEST_STOPDESC"),1));
        //TestCase2: checks to see if addStop returns false from adding stop to same location in list
        assertFalse(trip.addStop(new Stop(0,0,"TEST_STOPNAME","TEST_STOPID","TEST_STOPDESC"),1));
    }

    /**
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: tests getStop method functionality
     */
    @Test
    void getStop() {
        Stop testStop = new Stop(0,0,"TEST_STOPNAME","TEST_STOPID","TEST_STOPDESC");
        trip.addStop(testStop,1);

        //TestCase1: checks to see if getStop returns correct stop
        assertSame(trip.getStop("TEST_STOPID"),testStop);
        //TestCase2: checks to see if getStop returns null from non-existent stopID
        assertNull(trip.getStop(null));
    }

}