public class Time {

    private int hour;
    private int min;
    private int sec;

    /**
     * Author:
     * Description: Constructor that checks for invalid times and throws an
     * IllegalArgumentException if the time is an invalid format of an invalid time
     *
     * @param time - the value to be set for Time object.
     * @throws IllegalArgumentException
     */
    public Time(String time) throws IllegalArgumentException {
        try {
            String[] times = time.split(":");
            setHour(Integer.parseInt(times[0]));
            setMin(Integer.parseInt(times[1]));
            setSec(Integer.parseInt(times[2]));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("the time: " + time + " is not correctly formatted");
        }
    }

    /**
     * Author:
     * Description:
     *
     * @param hour
     * @param min
     * @param sec
     */
    public Time(int hour, int min, int sec) {
        this(String.format("%02d:%02d:%02d", hour, min, sec));
    }

    /**
     * Author:
     * Description: Returns an integer value of the time for comparison purposes.
     *
     * @return integer value of the time (from 0 to 239999)
     */
    public int getTime() {
        return (hour * 10000) + (min * 100) + sec;
    }

    /**
     * Author:
     * Description: sets hour location of time
     *
     * @param hour
     */
    public void setHour(int hour) throws NumberFormatException {
        if (hour >= 48 || hour < 0) {
            throw new NumberFormatException();
        }
        this.hour = hour;
    }

    /**
     * Author:
     * Description: sets min location of time
     *
     * @param min
     */
    public void setMin(int min) throws NumberFormatException {
        if (min >= 60 || min < 0) {
            throw new NumberFormatException();
        }
        this.min = min;
    }

    /**
     * Author:
     * Description: sets seconds location of time
     *
     * @param sec
     */
    public void setSec(int sec) throws NumberFormatException {
        if (sec >= 60 || min < 0) {
            throw new NumberFormatException();
        }
        this.sec = sec;
    }

    /**
     * Author: hortong
     * Description:
     *
     * @return string of this objects time value
     */
    public String toString() {
        return String.format("%02d:%02d:%02d", hour, min, sec);
    }

    /**
     * Author: hortong
     * Description: Compares the time values of two time objects
     * and returns an integer indicating which is greater.
     *
     * @param time
     * @return -1 if value of this time object is smaller
     * 1 if the value of this time object is greater
     * 0 if they are equal.
     */
    public int compareTo(Time time) {
        int result = 1;
        if (this.getTime() == time.getTime()) {
            result = 0;
        } else if (this.getTime() < time.getTime()) {
            result = -1;
        }
        return result;
    }
}
