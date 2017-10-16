import java.util.ArrayList;

/**
 * @author Gracie Horton
 * @version 1.0
 * @created 03-Oct-2017 4:57:34 PM
 */
public interface Subject {

	/**
	 * 
	 * @param observer
	 */
    void attach(Observer observer);

	/**
	 * 
	 * @param observer
	 */
    boolean detach(Observer observer);

	void notifyObservers(ArrayList<Object> itemsToSend);

}