import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * @author heinzja
 * @version 1.0
 * @created 03-Oct-2017 4:57:25 PM
 */
public class FileManager {

	private Collection<File> validFileList;
	private ArrayList<String> validFileTypes;
	public void Initialize(){
		validFileList = new ArrayList<File>();
		validFileTypes = new ArrayList<String>();
		addValidType();
	}

	/**
	 * 
	 * @param observer
	 */
	public void attach(Observer observer){

	}

	/**
	 * Author: joseph heinz heinzja@msoe.edu
	 * Description: current implementation only checks if first line follows format
	 * @param file - file which is to be checked for validity
	 */
	private boolean isValid(File file){
		boolean result = false;
		try {
			String firstLine = getFirstLine(file);
			for(int i=0;i<validFileTypes.size();i++){ //checks if valid file type
				if(firstLine.equals(validFileTypes.get(i))){
					//TODO: if necessary, iterate through entire file to check for validity
					result = true;
				}
			}

		} catch (Exception e) {
			System.err.println("TEST: isValid -> " + e);
		}

		return result;
	}

	/**
	 * Author: joseph heinz <heinzja@msoe.edu>
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
	 *
	 * @param filename
	 */
	public boolean rmFile(String filename){
		boolean result = false;
		//TODO: Complete rmFile

		return result;
	}

	/**
	 *
	 * @param filename - name of the file to get from Collection validFileList
	 * @return File - file found in list, returns NULL if file does not exist
	 */
	private File getFile(String filename){
		File result = null;
		//TODO: Complete getFile
		return result;
	}

	/**
	 * 
	 * @param file
	 */
	private ArrayList<Object> parseStopFile(File file) throws FileNotFoundException {
		ArrayList<Object> toReturn = new ArrayList<>();
		String stop_id;
		String stop_desc;
		String stop_lat;
		String stop_lon;
		String stop_name;
		Scanner scanner = new Scanner(file);
		scanner.nextLine();
		String line;
		while(scanner.hasNext()){
			line = scanner.nextLine();
			String[] items = line.split(",\\S");
			stop_id = items[0];
			stop_name = items[1];
			stop_desc = items[2];
			stop_lat = items[3];
			stop_lon = items[4];
			toReturn.add(new Stop(Float.parseFloat(stop_lon), Float.parseFloat(stop_lat),
					stop_name, stop_id, stop_desc));
		}
		return toReturn;
	}

	/**
	 * 
	 * @param file
	 */
	private ArrayList<Object> parseRouteFile(File file) throws FileNotFoundException {
		ArrayList<Object> toReturn = new ArrayList<>();
		String route_id;
		String agency_id;
		String route_short_name;
		String route_long_name;
		String route_desc;
		String route_type;
		String route_url;
		String route_color;
		String route_text_color;
		Scanner scanner = new Scanner(file);
		scanner.nextLine();
		String line;
		while(scanner.hasNext()){
				line = scanner.nextLine();
				String[] items = line.split(",\\S");
				route_id = items[0];
				agency_id = items[1];
				route_short_name = items[3];
				route_long_name = items[4];
				route_desc = items[5];
				route_type = items[6];
				route_url = items[7];
				toReturn.add(new Route(route_id, agency_id, route_short_name, route_long_name,
						route_desc, route_type, route_url));
		}
		return toReturn;
	}

	/**
	 * 
	 * @param file
	 */
	private ArrayList<Object> parseTripFile(File file) throws FileNotFoundException {
		ArrayList<Object> toReturn= new ArrayList<>();
		String route_id;
		String service_id;
		String trip_id;
		String trip_head_sign;
		String direction_id;
		String block_id;
		String shape_id;
		Scanner scanner = new Scanner(file);
		scanner.nextLine();
		String line;
		while(scanner.hasNext()) {
			line = scanner.nextLine();
			String[] items = line.split(",\\S");
			route_id = items[0];
			service_id = items[1];
			trip_id = items[2];
			trip_head_sign = items[3];
			direction_id = items[4];
			block_id = items[5];
			shape_id = items[6];
			toReturn.add(new Trip(trip_id, service_id, route_id, trip_head_sign, direction_id,
					block_id, shape_id));
		}
		return toReturn;
	}

	public ArrayList<Object> parseFile(File file){ //TODO: add specific action for each valid file type.
		ArrayList<Object> result = null;
		try {
			switch (getFirstLine(file)) {
				case "stop_id,stop_name,stop_desc,stop_lat,stop_lon":
					result = parseStopFile(file);
					break;
				case "route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color":
					result = parseRouteFile(file);
					break;
				case "trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type":
					result = parseTripFile(file);
					break;
				default:
					System.out.println("TEST: parseFile -> parse format of valid file not supported yet");
			}
		}catch (Exception e){
			System.out.println("TEST: parseFile -> " + e);
		}
		return result; 									//returns result of if the file was parsed correctly.
	}

	/**
	 *
	 * @return result - true if able to add file, false if file already exists.
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

	private String getFirstLine(File filename){
		String firstLine = null;
		try{
		InputStream in = Files.newInputStream(filename.toPath());
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		firstLine = reader.readLine();
		}catch (Exception e){
			System.out.println("TEST: getFirstLine -> " + e);
		}
		return firstLine;
	}


}