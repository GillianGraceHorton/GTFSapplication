import java.util.ArrayList;

/**
 * @author heinzja
 * @version 1.0
 * @created 03-Oct-2017 4:57:34 PM
 */
public interface Subject {

	/**
	 * 
	 * @param observer
	 */
	public void attach(Observer observer);

	/**
	 * 
	 * @param observer
	 */
	public void detach(Observer observer);

	public void notifyObservers(ArrayList<Object> itemsToSend);

}