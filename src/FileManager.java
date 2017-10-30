import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * @author Joseph Heinz - heinzja@msoe.edu
 * Created: 10/3/2017 4:57:25 PM
 */
public class FileManager {
    private NavigableMap<String, String> validFileTypes;

    /**
     * @author Joseph Heinz - heinzja@msoe.edu
     * Description: initializes FileManager and validFileTypes
     */
    public FileManager() {
        validFileTypes = new TreeMap<>();
        addValidType();
    }

    /**
     * Description: Parses information from a Stop file.
     * @param file containing stop objects
     * @return Array containing all the stop objects
     * @author hortong
     */
    public LinkedList<Stop> parseStopFile(File file) throws InputMismatchException,
            FileNotFoundException, NullPointerException {
        LinkedList<Stop> toReturn = new LinkedList<>();
        try {
            String stop_id, stop_desc, stop_name;
            double stop_lat, stop_lon;
            Scanner scanner = new Scanner(file);
            String firstLine = scanner.nextLine();
            if (!firstLine.equals(validFileTypes.get("stops"))) {
                throw new InputMismatchException("Error: Invalid Stops File format for: " + file.getName());
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
                    JOptionPane.showMessageDialog(null, "Error at line: " + line + "\n    " + e
                            .getMessage());
                }catch (NullPointerException e) {
                    throw new NullPointerException("ERROR: Invalid Stop File Format at the following line:\n" + line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("ERROR: " + file.getName() + " was not found.\n" + e.getMessage());
        }
        return toReturn;
    }

    /**
     * Description: Parses information from a Route file.
     * @param file containing route objects
     * @return Array containing all the route objects
     * @author hortong
     */
    public LinkedList<Route> parseRouteFile(File file) throws InputMismatchException,
            FileNotFoundException, NullPointerException {
        LinkedList<Route> toReturn = new LinkedList<>();
        try {
            String route_id, agency_id, route_short_name, route_long_name;
            String route_desc, route_type, route_url, route_color, route_text_color;
            Scanner scanner = new Scanner(file);
            String firstLine = scanner.nextLine();
            if (!firstLine.equals(validFileTypes.get("routes"))) {
                throw new InputMismatchException("Error: Invalid Route file format for: " + file.getName());
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
                    JOptionPane.showMessageDialog(null, "Error at line: " + line + "\n    " + e
                            .getMessage());
                }catch (NullPointerException e) {
                    throw new NullPointerException("ERROR: Invalid Route File Format at the following line:\n" + line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("ERROR: " + file.getName() + " was not found.\n" + e.getMessage());
        }
        return toReturn;
    }

    /**
     * Author: hortong
     * Description: Parses information from a Trip file.
     * @param file - file to parse for Trip data
     * @return - returns ArrayList full of parsed data from trip file.
     */
    public LinkedList<Trip> parseTripFile(File file) throws InputMismatchException,
            FileNotFoundException,
            NullPointerException {
        LinkedList<Trip> toReturn = new LinkedList<>();
        try {
            String route_id, service_id, trip_id, trip_head_sign, direction_id, block_id, shape_id;
            Scanner scanner = new Scanner(file);
            String firstLine = scanner.nextLine();
            if (!firstLine.equals(validFileTypes.get("trips"))) {
                throw new InputMismatchException("Error: Invalid Trip file format for: " + file.getName());
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
                    JOptionPane.showMessageDialog(null, "Error at line: " + line + "\n" + e
                            .getMessage());
                } catch (NullPointerException e) {
                    throw new NullPointerException("ERROR: Invalid Trip File Format at the following line:\n" + line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("ERROR: " + file.getName() + " was not found.\n" + e.getMessage());
        }
        return toReturn;
    }

    /**
     * Description: Parses information from a StopTimes file.
     * @param file - the file to parse for data
     * @return ArrayLists of StopTimes objects
     * @throws InputMismatchException Invalid StopTimes File format.
     * @throws FileNotFoundException File was not found
     * @throws NullPointerException Invalid StopTimes File Format at a specific line
     */
    public LinkedList<StopTime> parseStopTimesFile(File file)  throws InputMismatchException,
            FileNotFoundException, NullPointerException {
        LinkedList<StopTime> toReturn = new LinkedList<>();
        try {
            Scanner scanner = new Scanner(file);
            String firstLine = scanner.nextLine();
            //checks that the first line in the file matches the format for a stop_timesFile
            if (!firstLine.equals(validFileTypes.get("stop_times"))) {
                throw new InputMismatchException("Error: Invalid StopTimes File format for: " + file.getName());
            }
            @SuppressWarnings("SpellCheckingInspection") String line, trip_id, arrival_time, departure_time, stop_id, stop_sequence, stop_headsign,
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
                    /* Catches an error in creating the stopTime object and adds it to the
                        ArrayList of error messages then continues going through the lines in the file.*/
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Error at line: " + line + "\n\t" + e
                            .getMessage());
                }catch (NullPointerException e) {
                    throw new NullPointerException("ERROR: Invalid StopTimes File Format at the following line:\n"+line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("ERROR: " + file.getName() + " was not found.\n" + e.getMessage());
        }
        return toReturn;
    }

    /**
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: adds valid file types to an ArrayList of valid file types.
     */
    private void addValidType() {
        String agencyFormat = "agency_id,agency_name,agency_url,agency_timezone,agency_phone";
        String calenderFormat = "service_id,monday,tuesday,wednesday,thursday,friday,saturday,sunday,start_date,end_date";
        String calenderDatesFormat = "service_id,date,exception_type";
        String fairAttributedFormat = "fare_id,price,currency_type,payment_method,transfers,transfer_duration,agency_id";
        String feedInfoFormat = "feed_publisher_name,feed_publisher_url,feed_lang";
        String routesFormat = "route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color";
        String shapesFormat = "shape_id,shape_pt_lat,shape_pt_lon,shape_pt_sequence";
        @SuppressWarnings("SpellCheckingInspection") String stopTimesFormat = "trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type";
        String stopsFormat = "stop_id,stop_name,stop_desc,stop_lat,stop_lon";
        String transfersFormat = "from_stop_id,to_stop_id,transfer_type";
        @SuppressWarnings("SpellCheckingInspection") String tripsFormat = "route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id";

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
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: exports stops to user chosen directory
     * @param exportName - File containing the desired directory and name of the file to export.
     * @param ds - DataStorage object which holds all stored Stop objects.
     * @throws IOException Unable to create file for export
     */
    public void exportStopFile(File exportName, DataStorage ds) throws IOException {
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
        } catch (IOException e) {
            throw new IOException("Error: Unable to create: " + exportName.getName(), e);
        }
        System.out.println("TEST: exportStopFile completed");
    }

    /**
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: Creates Route file in user chosen directory
     * @param exportName - File containing the desired directory and name of the file to export
     * @param ds - DataStorage object which holds all stored Route objects
     * @throws IOException Unable to create file for export
     */
    public void exportRouteFile(File exportName, DataStorage ds) throws IOException {
        File exportDir = new File(exportName.getParent(), "exports");
        try {
            if (!exportDir.exists()) {
                exportDir.mkdir();
                System.out.println("TEST: exportRouteFile -> exports directory created");
            }
            File exportFile = new File(exportDir.getPath(), exportName.getName() + ".txt");
            if (!exportFile.exists()) {
                exportFile.createNewFile();
            }

            PrintWriter pw = new PrintWriter(exportFile, "UTF-8");
            pw.println(validFileTypes.get("routes"));
            NavigableMap<String, Route> routes = ds.getRoutes();
            NavigableSet<String> nav = routes.navigableKeySet();
            for (String id : nav) {
                pw.println(routes.get(id).toStringExport());
            }
            pw.close();
        } catch (IOException e) {
            throw new IOException("Error: Unable to create: " + exportName.getName(), e);
        }
        System.out.println("TEST: exportRouteFile completed");
    }

    /**
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: Creates Trip file in user chosen directory
     * @param exportName - File containing the desired directory and name of the file to export
     * @param ds - DataStorage object which holds all stored Trip objects
     * @throws IOException Unable to create file for export
     */
    public void exportTripFile(File exportName, DataStorage ds) throws IOException {
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
            pw.println(validFileTypes.get("trips"));
            NavigableMap<String, Trip> trips = ds.getTrips();
            NavigableSet<String> nav = trips.navigableKeySet();
            for (String id : nav) {
                pw.println(trips.get(id).toStringExport());
            }
            pw.close();
        } catch (IOException e) {
            throw new IOException("Error: Unable to create: " + exportName.getName(), e);
        }
        System.out.println("TEST: exportTripFile completed");
    }

    /**
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: Creates StopTimes file in user chosen directory
     * @param exportName - File containing the desired directory and name of the file to export
     * @param ds - DataStorage object which holds all stored StopTime objects
     * @throws IOException Unable to create file for export
     */
    public void exportStopTimesFile(File exportName, DataStorage ds) throws IOException {
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
            pw.println(validFileTypes.get("stop_times"));
            for (StopTime tmp : ds.getStopTimes()) {
                pw.println(tmp.toStringExport());
            }
            pw.close();
        } catch (IOException e) {
            throw new IOException("Error: Unable to create: " + exportName.getName(), e);
        }
        System.out.println("TEST: exportStopTimesFile completed");
    }

    /**
     * Author: hortong
     * Description: Displays message window to the user containing all the parsing errors encountered that
     *              didn't break the flow of the program.
     * @param fileName
     * @param errors
     */
    private void errorsInParsing(String fileName, ArrayList<String> errors){
        StringBuilder warning = new StringBuilder();
        for (String error:errors) {
            warning.append(error);
        }
        JOptionPane.showMessageDialog(null, warning.toString(), "Errors from parsing the file: " + fileName,
                JOptionPane.ERROR_MESSAGE);
    }
}