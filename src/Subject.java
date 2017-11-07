/**
 * Author: Gracie Horton
 * Description:
 * Date Created: 10/3/2017 - 4:57:34 PM
 */
public interface Subject {

    /**
     * Author: Gracie Horton
     * Description:
     *
     * @param observer
     */
    void attach(Observer observer);

    /**
     * Author: Gracie Horton
     * Description:
     *
     * @param observer
     */
    boolean detach(Observer observer);

    /**
     * Author: Gracie Horton
     * Description:
     */
    void notifyObservers();

}