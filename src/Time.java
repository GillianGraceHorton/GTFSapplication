public class Time {

    private int hour;
    private int min;
    private int sec;



    /**
     * constructor that checks for invalid times and throws IllegalArgumentException if the time
     * is of invalid format of an invalid time
     * @param time
     */
    public Time(String time){
        try {
            String[] times = time.split(":");
            setHour(Integer.parseInt(times[0]));
            setMin(Integer.parseInt(times[1]));
            setSec(Integer.parseInt(times[2]));
        }catch(IndexOutOfBoundsException | NumberFormatException e){
            throw new IllegalArgumentException("the time: " + time + " is not correctly formatted");
        }
    }

    public Time(int hour, int min, int sec){
        this(String.format("%02d:%02d:%02d", hour, min, sec));
    }

    /**
     * returns an integer value of the time for comparison purposes.
     * @return integer value of the time (from 0 to 239999)
     */
    public int getTime() {
        return (hour*10000) + (min*100) + sec;
    }

    public void setHour(int hour) {
        if(hour>=24 || hour < 0){
            throw new NumberFormatException();
        }
        this.hour = hour;
    }

    public void setMin(int min) {
        if(min>=60||min<0){
            throw new NumberFormatException();
        }
        this.min = min;
    }

    public void setSec(int sec) {
        if(sec>=60 || min < 0){
            throw new NumberFormatException();
        }
        this.sec = sec;
    }

    /**
     * @author hortong
     * @return string of this objects time value
     */
    public String toString(){
        return String.format("%02d:%02d:%02d", hour, min, sec);
    }

    /**
     * @author hortong
     * compares the time values of two time objects and returns an integer indicating which is
     * greater
     * @param time
     * @return -1 if value of this time object is smaller
     * 1 if the value of this time object is greater
     * 0 if they are equal.
     */
     public int compareTo(Time time){
        if(this.getTime() == time.getTime()){
            return 0;
        }else if(this.getTime() < time.getTime()){
            return -1;
        }
        return 1;
    }
}
