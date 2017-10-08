import java.io.*;
import java.nio.file.*;
import java.util.Collection;

/**
 * @author heinzja
 * @version 1.0
 * @created 03-Oct-2017 4:57:25 PM
 */
public class FileManager {

	private Collection<File> validFileList;
	private static Collection<Observer> observers;


	public void FileManager(){

	}

	/**
	 * 
	 * @param observer
	 */
	public void attach(Observer observer){

	}

	/**
	 * Author: joseph heinz heinzja@msoe.edu
	 * Description:
	 * @param file - file which is to be checked for validity
	 */
	private boolean isValid(File file){
		boolean result = false;
		Path filePath = file.toPath();
		try (InputStream in = Files.newInputStream(filePath);
			 BufferedReader reader = new BufferedReader(new InputStreamReader(in)))
		{
			String line;
			while ((line = reader.readLine()) != null) {
					//check first line, make sure all following lines follow that format.
				for(int i = 0; i < line.length();i++){

				}
			}
		} catch (IOException io) {
			System.err.println(io);
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
                    Path dir = Paths.get(System.getProperty("user.dir").toString() + "/validfiles");        //if not, gets users directory to application
                    Files.copy(file.toPath(), dir.resolve(file.getName()));                                      //copies file data from passed file to new directory
                    result = true;
                } else {
                    System.out.println("TEST: file with that name already exists");
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
	 * @param observer
	 */
	public void detach(Observer observer){

	}

	/**
	 * 
	 * @param created
	 */
	public void notifyObservers(Collection<Observer> created){

	}

	/**
	 * 
	 * @param filename
	 */
	public boolean rmFile(String filename){
		return false;
	}

	/**
	 * 
	 * @param file
	 */
	public Stop parseStopFile(File file){
		return null;
	}

	/**
	 * 
	 * @param file
	 */
	public Route parseRouteFile(File file){
		return null;
	}

	/**
	 * 
	 * @param file
	 */
	public Trip parseTripFile(File file){
		return null;
	}

}