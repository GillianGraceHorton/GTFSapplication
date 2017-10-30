

import javax.swing.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.security.InvalidParameterException;
import java.util.*;

/**
 * @author heinzja
 * @version 1.0
 * Created: 03-Oct-2017 4:57:25 PM
 */
public class FileManager {
    private NavigableMap<String, String> validFileTypes;

    /**
     * @author Joseph Heinz - heinzja@msoe.edu
     * Description: initializes validFileList and validFileTypes
     */
    public FileManager() {
        validFileTypes = new TreeMap<>();
        addValidType();
    }

    /**
     * parses the information from a file containing stop objects.
     *
     * @param file containing stop objects
     * @return Array containing all the stop objects
     * @author hortong
     */
    public LinkedList<Object> parseStopFile(File file) throws Exception {
        LinkedList<Object> toReturn = new LinkedList<>();
        ArrayList<String> errors = new ArrayList<>();
        try {
            String stop_id, stop_desc, stop_name;
            double stop_lat, stop_lon;
            Scanner scanner = new Scanner(file);
            String firstLine = scanner.nextLine();
            if (!firstLine.equals(validFileTypes.get("stops"))) {
                throw new Exception("the file: " + file.getName() + ", was not " +
                        "formatted correctly for a stop file");
            }
            String line;
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                try {
                    String[] items = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    stop_id = items[0];
                    stop_name = items[1];
                    stop_desc = items[2];
                    stop_lat = Double.parseDouble(items[3]);
                    stop_lon = Double.parseDouble(items[4]);
                    toReturn.add(new Stop(stop_lon, stop_lat, stop_name, stop_id, stop_desc));
                }catch(IllegalArgumentException e){
                    errors.add("Error at line: " + line + "\n    " + e.getMessage());
                }catch (NullPointerException e) {
                    throw new NullPointerException("ERROR: Invalid Stop File Format at the " +
                            "following line:\n    " + line);
                }
            }
            if(!errors.isEmpty()){
                errorsInParsing(file.getName(), errors);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("ERROR: The following file could not be found: " +
                    file.getName() + "\n" + e.getMessage());
        }
        return toReturn;
    }

    /**
     * parses the information from a file containing route objects.
     *
     * @param file containing route objects
     * @return Array containing all the route objects
     * @author hortong
     */
    public LinkedList<Object> parseRouteFile(File file) throws Exception {
        LinkedList<Object> toReturn = new LinkedList<>();
        ArrayList<String> errors = new ArrayList<>();
        try {
            String route_id, agency_id, route_short_name, route_long_name;
            String route_desc, route_type, route_url, route_color, route_text_color;
            Scanner scanner = new Scanner(file);
            String firstLine = scanner.nextLine();
            if (!firstLine.equals(validFileTypes.get("routes"))) {
                throw new Exception("the file: " + file.getName() + ", was not " +
                        "formatted correctly for a route file");
            }
            String line;
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                try{
                String[] items = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                route_id = items[0];
                agency_id = items[1];
                route_short_name = items[2];
                route_long_name = items[3];
                route_desc = items[4];
                route_type = items[5];
                route_url = items[6];
                route_color = items[7];
                route_text_color = items[8];
                toReturn.add(new Route(route_id, agency_id, route_short_name, route_long_name,
                        route_desc, route_type, route_url, route_color, route_text_color));
                }catch(IllegalArgumentException e){
                    errors.add("Error at line: " + line + "\n    " + e.getMessage());
                }catch (NullPointerException e) {
                    throw new NullPointerException("ERROR: Invalid Route File Format at the " +
                            "following line:\n    " + line);
                }
            }
            if(!errors.isEmpty()){
                errorsInParsing(file.getName(), errors);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("ERROR: The following file could not be found: " +
                    file.getName() + "\n" + e.getMessage());
        }
        return toReturn;
    }

    /**
     * @param file - file to parse for Trip data
     * @return - returns ArrayList full of parsed data from trip file.
     * @author hortong
     */
    public LinkedList<Object> parseTripFile(File file) throws Exception {
        LinkedList<Object> toReturn = new LinkedList<>();
        ArrayList<String> errors = new ArrayList<>();
        try {
            String route_id, service_id, trip_id, trip_head_sign, direction_id, block_id, shape_id;
            Scanner scanner = new Scanner(file);
            String firstLine = scanner.nextLine();
            if (!firstLine.equals(validFileTypes.get("trips"))) {
                throw new Exception("the file: " + file.getName() + ", was not " +
                        "formatted correctly for a trips file");
            }
            String line;
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                try {
                    String[] items = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    route_id = items[0];
                    service_id = items[1];
                    trip_id = items[2];
                    trip_head_sign = items[3];
                    direction_id = items[4];
                    block_id = items[5];
                    shape_id = items[6];
                    toReturn.add(new Trip(trip_id, service_id, route_id, trip_head_sign, direction_id, block_id, shape_id));
                }catch(IllegalArgumentException e){
                    errors.add("Error at line: " + line + "\n    " + e.getMessage());
                } catch (NullPointerException e) {
                    throw new NullPointerException("ERROR: Invalid Trip File Format at the " +
                            "following line:\n    " + line);
                }
            }
            if(!errors.isEmpty()){
                errorsInParsing(file.getName(), errors);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("ERROR: The following file could not be found: " +
                    file.getName() + "\n" + e.getMessage());
        }
        return toReturn;
    }

    public LinkedList<Object> parseStopTimesFile(File file) throws Exception {
        LinkedList<Object> toReturn = new LinkedList<>();
        try {
            Scanner scanner = new Scanner(file);
            String firstLine = scanner.nextLine();
            //checks that the first line in the file matches the format for a stop_timesFile
            if (!firstLine.equals(validFileTypes.get("stop_times"))) {
                throw new InvalidParameterException("the file: " + file.getName() + ", was not " +
                        "formatted correctly for a stopTimes file");
            }
            String line, trip_id, arrival_time, departure_time, stop_id, stop_sequence,
                    stop_headsign,
                    pickup_type, drop_off_type;
            //reads each of the lines in the stop_times file, creates a new stopTimes object from
            // the information, and adds it to the toReturn ArrayList of stopTime objects.
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                try {
                    String[] items = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    trip_id = items[0];
                    arrival_time = items[1];
                    departure_time = items[2];
                    stop_id = items[3];
                    stop_sequence = items[4];
                    stop_headsign = items[5];
                    pickup_type = items[6];
                    drop_off_type = items[7];
                    toReturn.add(new StopTime(trip_id, arrival_time, departure_time, stop_id,
                            stop_sequence, stop_headsign, pickup_type, drop_off_type));
                    //catches an error in creating the stopTime object and adds it to the
                    // ArrayList of error messages then continues going through the lines in the
                    // file.
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Error at line: " + line + "\n\t" + e
                            .getMessage());
                }catch (NullPointerException e) {
                    throw new NullPointerException("ERROR: Invalid StopTimes File Format at the " +
                            "following line:\n    " + line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("ERROR: The following file could not be found: " +
                    file.getName() + "\n" + e.getMessage());
        }catch(StackOverflowError e){
            System.out.println("here");
        }
        return toReturn;
    }

    /**
     * @author Joseph Heinz - heinzja@msoe.edu
     * Temporary valid file type arraylist will be changed to be less static in the future.
     * Description: adds valid file types to an arraylist of valid file types.
     */
    private void addValidType() {
        String agencyFormat = "agency_id,agency_name,agency_url,agency_timezone,agency_phone";
        String calenderFormat = "service_id,monday,tuesday,wednesday,thursday,friday,saturday,sunday,start_date,end_date";
        String calenderDatesFormat = "service_id,date,exception_type";
        String fairAttributedFormat = "fare_id,price,currency_type,payment_method,transfers,transfer_duration,agency_id";
        String feedInfoFormat = "feed_publisher_name,feed_publisher_url,feed_lang";
        String routesFormat = "route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color";
        String shapesFormat = "shape_id,shape_pt_lat,shape_pt_lon,shape_pt_sequence";
        String stopTimesFormat = "trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type";
        String stopsFormat = "stop_id,stop_name,stop_desc,stop_lat,stop_lon";
        String transfersFormat = "from_stop_id,to_stop_id,transfer_type";
        String tripsFormat = "route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id";

        validFileTypes.put("agency", agencyFormat);
        validFileTypes.put("calender", calenderFormat);
        validFileTypes.put("calender_dates", calenderDatesFormat);
        validFileTypes.put("fare_attributes", fairAttributedFormat);
        validFileTypes.put("feed_info", feedInfoFormat);
        validFileTypes.put("routes", routesFormat);
        validFileTypes.put("shapes", shapesFormat);
        validFileTypes.put("stop_times", stopTimesFormat);
        validFileTypes.put("stops", stopsFormat);
        validFileTypes.put("transfers", transfersFormat);
        validFileTypes.put("trips", tripsFormat);
    }

    /**
     * @param exportName the file containing the desired directory and name of the file to export
     * @param ds         the DataStorage object which holds all the stops
     * @author Joseph Heinz - heinzja@msoe.edu
     * Description: exports stops to user choosen directory
     */
    public void exportStopFile(File exportName, DataStorage ds) throws Exception {
        File exportDir = new File(exportName.getParent(), "exports");
        try {
            if (!exportDir.exists()) {
                exportDir.mkdir();
            }
            File exportFile = new File(exportDir.getPath(), exportName.getName() + ".txt");
            if (!exportFile.exists()) {
                exportFile.createNewFile();
            }

            PrintWriter pw = new PrintWriter(exportFile, "UTF-8");
            pw.println("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
            NavigableMap<String, Stop> stops = ds.getStops();
            NavigableSet<String> nav = stops.navigableKeySet();
            for (String id : nav) {
                pw.println(stops.get(id).toStringExport());
            }
            pw.close();
            //TODO change from generic Exception
        } catch (Exception e) {
            throw new Exception("there was a problem writing the stop objects to the specified " +
                    "file: " + exportName.getName(), e);
        }
        System.out.println("TEST: exportStopFile completed");
    }

    /**
     * @param exportName the file containing the desired directory and name of the file to export
     * @param ds         the DataStorage object which holds all the routes
     * @author Joseph Heinz - heinzja@msoe.edu
     * Description: exports routes to user choosen directory
     */
    public void exportRouteFile(File exportName, DataStorage ds) throws Exception {
        File exportDir = new File(exportName.getParent(), "exports");
        try {
            if (!exportDir.exists()) {
                exportDir.mkdir();
                System.out.println("TEST: exportRouteFile -> exports file created");
            }
            File exportFile = new File(exportDir.getPath(), exportName.getName() + ".txt");
            if (!exportFile.exists()) {
                exportFile.createNewFile();
            }

            PrintWriter pw = new PrintWriter(exportFile, "UTF-8");
            pw.println("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color");
            NavigableMap<String, Route> routes = ds.getRoutes();
            NavigableSet<String> nav = routes.navigableKeySet();
            for (String id : nav) {
                pw.println(routes.get(id).toStringExport());
            }
            pw.close();
            //TODO change from generic exception
        } catch (Exception e) {
            throw new Exception("there was a problem writing the route objects to the specified " +
                    "file: " + exportName.getName(), e);
        }
        System.out.println("TEST: exportRouteFile completed");
    }

    /**
     * @param exportName the file containing the desired directory and name of the file to export
     * @param ds         the DataStorage object which holds all the trips
     * @author Joseph Heinz - heinzja@msoe.edu
     * Description: exports trips to user choosen directory
     */
    public void exportTripFile(File exportName, DataStorage ds) throws Exception {
        File exportDir = new File(exportName.getParent(), "exports");
        try {
            if (!exportDir.exists()) {
                exportDir.mkdir();
                System.out.println("TEST: exportTripFile -> exports file created");
            }
            File exportFile = new File(exportDir.getPath(), exportName.getName() + ".txt");
            if (!exportFile.exists()) {
                exportFile.createNewFile();
            }

            PrintWriter pw = new PrintWriter(exportFile, "UTF-8");
            pw.println("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
            NavigableMap<String, Trip> trips = ds.getTrips();
            NavigableSet<String> nav = trips.navigableKeySet();
            for (String id : nav) {
                pw.println(trips.get(id).toStringExport());
            }
            pw.close();
            //TODO change from generic exception
        } catch (Exception e) {
            throw new Exception("there was a problem writing the trip objects to the specified " +
                    "file: " + exportName.getName(), e);
        }
        System.out.println("TEST: exportTripFile completed");
    }

    /**
     * @param exportName the file containing the desired directory and name of the file to export
     * @param ds         the DataStorage object which holds all the stop times
     * @author Joseph Heinz - heinzja@msoe.edu
     * Description: exports all stored stop times to user choosen directory
     */
    public void exportStopTimesFile(File exportName, DataStorage ds) throws Exception {
        File exportDir = new File(exportName.getParent(), "exports");
        try {
            if (!exportDir.exists()) {
                exportDir.mkdir();
                System.out.println("TEST: exportStopTimesFile -> exports file created");
            }
            File exportFile = new File(exportDir.getPath(), exportName.getName() + ".txt");
            if (!exportFile.exists()) {
                exportFile.createNewFile();
            }
            PrintWriter pw = new PrintWriter(exportFile, "UTF-8");
            pw.println("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type");
            for (StopTime tmp : ds.getStopTimes()) {
                pw.println(tmp.toStringExport());
            }
            pw.close();
            //TODO change from generic exception
        } catch (Exception e) {
            throw new Exception("there was a problem writing the stopTime objects to the " +
                    "specified " +
                    "file: " + exportName.getName(), e);
        }
        System.out.println("TEST: exportStopTimesFile completed");
    }

    /**
     * displays message window to the user containing all the parsing errors encountered that
     * didn't break the flow of the program
     * @author hortong
     * @param fileName
     * @param errors
     */
    private void errorsInParsing(String fileName, ArrayList<String> errors){
        String warning = "";
        for (String error:errors) {
            warning += error;
        }
        JOptionPane.showMessageDialog(null, warning, "Errors from parsing the file: "
                        + fileName,
                JOptionPane.ERROR_MESSAGE);
    }
}