import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author heinzja
 * @version 1.0
 * Created: 03-Oct-2017 4:57:25 PM
 */
public class FileManager {

	private Collection<File> validFileList;
	private ArrayList<String> validFileTypes;

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Description: initializes validFileList and validFileTypes
	 */
	public FileManager(){
		validFileList = new ArrayList<File>();
		validFileTypes = new ArrayList<String>();
		addValidType();
	}

	/**
	 * @author hortong
	 * @return
	 */
	public ArrayList<Object> loadFromValidFiles(){
		ArrayList<Object> itemsToReturn = new ArrayList<Object>();
		File validFiles = new File("validfiles");
		//TODO: validFiles.list() may cause a NullPointerException
		try {
			if (validFiles.exists()) {
				for (String fileName : validFiles.list()) {
					itemsToReturn.addAll(parseFile(new File(validFiles, fileName)));
				}
			}
		}catch (Exception e){
			System.out.println("TEST: loadFromValidFiles -> " + e);
		}
		return itemsToReturn;
	}

	/**
	 * @author joseph heinz heinzja@msoe.edu
	 * Description: current implementation only checks if first line follows valid list of formats
	 * @param file - file which is to be checked for validity
	 */
	private boolean isValid(File file){
		boolean result = false;
		try {
			String firstLine = getFirstLine(file);
			for(int i=0;i<validFileTypes.size();i++){ //checks if valid file type
				if(firstLine.equals(validFileTypes.get(i))){
					//TODO: Future implementation/testing, iterate through entire file before valid
					result = true;
				}
			}

		} catch (Exception e) {
			System.err.println("TEST: isValid -> " + e);
		}

		return result;
	}

	/**
	 * @author joseph heinz - heinzja@msoe.edu
     * Description: Checks if file is of valid format before adding it to the 'validfiles' directory.
     *              If the directory 'validfiles' does not exist, its creates the directory and add the file to it.
	 * @param file File which is to be added to validFileList
	 */
	public boolean addFile(File file){
        boolean result = false;                                                                            //initializes result, false by default
		if(isValid(file)) {                                                                                     //checks if file is valid
            File newDir = new File("validfiles");                                                        //makes temp file with validfiles folder dir
            File newFile = new File(newDir,file.getName());                                                        //makes temp file with passed in files name
            try {
                if (!newDir.exists()) {                                                                         //checks to see if validfiles dir exists
                    newDir.mkdir();                                                                      			//if not, creates folder directory
                } else {
                    System.out.println("TEST: directory path already exist");
                }

                if (!newFile.exists()) {                                                                        //checks if passed file already exists in dir
                    Path dir = Paths.get(System.getProperty("user.dir") + "/validfiles");        //if not, gets users directory to application
                    Files.copy(file.toPath(), dir.resolve(file.getName()));                                      //copies file data from passed file to new directory
                    result = true;
                } else {
                    System.out.println("TEST: " + newFile.getName() + " already exists");
                }

            } catch (IOException io) {
                System.out.println("IO Exception");
            }
        }else{
		    System.out.println("Error: File is not valid format.");
        }
		return result;                                                                                        //returns result true - file added, false - file not added
	}

	/**
	 * @author
	 *	Description: removes file from 'validfiles' directory
	 * @param filename the name of the file to remove
	 */
	public boolean rmFile(String filename){
		boolean result = false;
		//TODO: Complete rmFile
		return result;
	}

	/**
	 *	Description: returns specified file stored in Collection validFileList
	 * @param filename - name of the file to return
	 * @return File - file found in list, returns NULL if file does not exist
	 */
	private File getFile(String filename){
		File result = null;
		//TODO: Complete getFile
		return result;
	}

	/**
     * parses the information from a file containing stop objects.
	 * @author hortong
	 * @param file containing stop objects
     * @return Array containing all the stop objects
	 */
	private ArrayList<Object> parseStopFile(File file) throws FileNotFoundException {
		ArrayList<Object> toReturn = new ArrayList<>();
		String stop_id, stop_desc,stop_name;
		double stop_lat,stop_lon;
		Scanner scanner = new Scanner(file);
		scanner.nextLine();
		String line;
		while(scanner.hasNext()){
			line = scanner.nextLine();
			String[] items = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			stop_id = items[0];
			stop_name = items[1];
			stop_desc = items[2];
			stop_lat = Double.parseDouble(items[3]);
			stop_lon = Double.parseDouble(items[4]);
			toReturn.add(new Stop(stop_lon, stop_lat, stop_name, stop_id, stop_desc));
		}
		return toReturn;
	}

	/**
	 * parses the information from a file containing route objects.
     * @author hortong
     * @param file containing route objects
     * @return Array containing all the route objects
	 */
	private ArrayList<Object> parseRouteFile(File file) throws FileNotFoundException {
		ArrayList<Object> toReturn = new ArrayList<>();
		String route_id,agency_id,route_short_name,route_long_name;
		String route_desc,route_type,route_url,route_color,route_text_color;
		Scanner scanner = new Scanner(file);
		scanner.nextLine();
		String line;
		while(scanner.hasNext()){
				line = scanner.nextLine();
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
						route_desc, route_type, route_url,route_color,route_text_color));
		}
		return toReturn;
	}

	/**
	 * @author hortong
	 * @param file - file to parse for Trip data
	 * @return - returns ArrayList full of parsed data from trip file.
	 */
	private ArrayList<Object> parseTripFile(File file) throws FileNotFoundException {
		ArrayList<Object> toReturn= new ArrayList<>();
		String route_id,service_id,trip_id,trip_head_sign,direction_id,block_id,shape_id;
		Scanner scanner = new Scanner(file);
		scanner.nextLine();
		String line;
		while(scanner.hasNext()) {
			line = scanner.nextLine();
			String[] items = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			route_id = items[0];
			service_id = items[1];
			trip_id = items[2];
			trip_head_sign = items[3];
			direction_id = items[4];
			block_id = items[5];
			shape_id = items[6];
			toReturn.add(new Trip(trip_id, service_id, route_id, trip_head_sign, direction_id, block_id, shape_id));
		}
		return toReturn;
	}

	private ArrayList<Object> parseStopTimesFile(File file) throws FileNotFoundException {
	    ArrayList<Object> toReturn = new ArrayList<>();
	    String trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,
                pickup_type,drop_off_type;
	    Scanner scanner = new Scanner(file);
	    scanner.nextLine();
	    String line;
	    while(scanner.hasNext()){
            line = scanner.nextLine();
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
        }
        return toReturn;
    }

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Description: Checks what type of file is to be parsed, and sends it to the correct parse method.
	 * @param  file - the file to parse for data
	 * @return returns an ArrayList of objects which contain the parsed data from the file.
	 */
	public ArrayList<Object> parseFile(File file){
		ArrayList<Object> result = null;
		try {
			//TODO: as we add more functionality, add specific parse for each valid file type.
			switch (getFirstLine(file)) {
				case "stop_id,stop_name,stop_desc,stop_lat,stop_lon":
					result = parseStopFile(file);
					break;
				case "route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color":
					result = parseRouteFile(file);
					break;
				case "route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id":
					result = parseTripFile(file);
					break;
				case "trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type":
					result = parseStopTimesFile(file);
					break;
				default:
					System.out.println("TEST: parseFile -> parse format of valid file not supported yet");
			}
		}catch (Exception e){
			System.out.println("TEST: parseFile -> " + e);
		}
		return result; 													//returns result of if the file was parsed correctly.
	}

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Temporary valid file type arraylist will be changed to be less static in the future.
	 * Description: adds valid file types to an arraylist of valid file types.
	 */
	private void addValidType(){
		validFileTypes.add("agency_id,agency_name,agency_url,agency_timezone,agency_phone");
		validFileTypes.add("service_id,monday,tuesday,wednesday,thursday,friday,saturday,sunday,start_date,end_date");
		validFileTypes.add("service_id,date,exception_type");
		validFileTypes.add("fare_id,price,currency_type,payment_method,transfers,transfer_duration,agency_id");
		validFileTypes.add("feed_publisher_name,feed_publisher_url,feed_lang");
		validFileTypes.add("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color");
		validFileTypes.add("shape_id,shape_pt_lat,shape_pt_lon,shape_pt_sequence");
		validFileTypes.add("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type");
		validFileTypes.add("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
		validFileTypes.add("from_stop_id,to_stop_id,transfer_type");
		validFileTypes.add("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
	}

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Description: Returns the first line of a given file, used for file validity check
	 * @param file - the file to get the first line of text from.
	 * @return returns string of first line of text from file.
	 */
	private String getFirstLine(File file){
		String firstLine = null;
		try{
			InputStream in = Files.newInputStream(file.toPath());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			firstLine = reader.readLine();
		}catch (Exception e){
			System.out.println("TEST: getFirstLine -> " + e);
		}
		return firstLine;
	}

	public void exportStopFile(File exportName, DataStorage ds){
		File exportDir = new File(exportName.getParent(),"exports");
		try {
			if(!exportDir.exists()){
				exportDir.mkdir();
			}
			File exportFile = new File(exportDir.getPath(),exportName.getName() + ".txt");
			if (!exportFile.exists()) {
				exportFile.createNewFile();
			}

			PrintWriter pw = new PrintWriter(exportFile, "UTF-8");
			pw.println("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
			Iterator<Stop> itr = ds.getStops().iterator();
			while(itr.hasNext()){
				Stop tmp = itr.next();
				String stopID,stopName,stopDescription;
				double stopLat,stopLon;
				stopID = tmp.getStopID();
				stopName = tmp.getName();
				stopDescription = tmp.getStopDescription();
				stopLat = tmp.getLocation().getLat();
				stopLon = tmp.getLocation().getLon();
				pw.format("%s,%s,%s,%f,%f\n",stopID,stopName,stopDescription,stopLat,stopLon);
			}
			pw.close();
		}catch (Exception e){
			System.out.println("TEST: exportStopFile -> " + e);
		}
		System.out.println("TEST: exportStopFile completed");
	}

	private void exportRouteFile(File filename, DataStorage dataStorage){

	}

	private void exportTripFile(File filename, DataStorage dataStorage){

	}
	private void exportStopTimesFile(File filename, DataStorage dataStorage){

	}

}