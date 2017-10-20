public class Time {

    int time;

    /**
     * returns the integer value of the time.
     * @return
     */
    public int getTime() {
        return time;
    }

    
    public Time(String time){
        String tempTime = time.replaceAll(":", "");
        int convertedTime;
        try{
            if(tempTime.length() != 6){
                throw new NumberFormatException();
            }
            convertedTime = Integer.parseInt(tempTime);
            if (convertedTime > 120000 || convertedTime < 0){
                throw new NumberFormatException();
            }
        }catch(NumberFormatException e){
            throw new IllegalArgumentException("the time: " + time + " is not correctly formatted");
        }
            this.time = convertedTime;
    }

    /**
     * @author hortong
     * @return string of this objects time value
     */
    public String toString(){
        String time = "" + this.time;
        for(int i = time.length(); i < 6; i++){
            time = "0" + time;
        }
        time = time.substring(0, 2) + ":" + time.substring(2);
        time = time.substring(0, 5) + ":" + time.substring(5);
        return time;
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
    private int comparteTo(Time time){
        if(this.getTime() > time.getTime()){
            return 1;
        }else if (this.getTime() < time.getTime()){
            return -1;
        }else{
            return 0;
        }
    }
}
