public class SimpleTimer {
    private double startTime;
    private double endTime;

    public SimpleTimer() {
        this.startTime = 0;
        this.endTime = 0;
    }

    public void start() {
        this.startTime = System.nanoTime();
    }

    public void end() {
        this.endTime = System.nanoTime();
    }

    private double getEndTime() {
        return this.endTime;
    }

    private double getStartTime() {
        return this.startTime;
    }

    public void result() {
        System.out.printf("%.6f seconds\n", (this.endTime - this.startTime) / 1000000000);
    }
}
