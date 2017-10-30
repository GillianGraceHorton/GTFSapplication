import java.util.ArrayList;

/**
 * Author: Gracie Horton
 * Description:
 * Date Created: 10/3/2017 - 4:57:30 PM
 */
public interface Observer {

	/**
	 * Description:
	 * @param updatedItems
	 */
	void update(ArrayList<Object> updatedItems);

}