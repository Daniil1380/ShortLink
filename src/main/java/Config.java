public class Config {

    private int maxDaysOfLiveTime;
    private int maxAttempts;

    public Config(int maxDaysOfLiveTime, int maxAttempts) {
        this.maxDaysOfLiveTime = maxDaysOfLiveTime;
        this.maxAttempts = maxAttempts;
    }

    public Config() {
    }

    public int getMaxDaysOfLiveTime() {
        return maxDaysOfLiveTime;
    }

    public void setMaxDaysOfLiveTime(int maxDaysOfLiveTime) {
        this.maxDaysOfLiveTime = maxDaysOfLiveTime;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    @Override
    public String toString() {
        return "Config{" +
                "maxDaysOfLiveTime=" + maxDaysOfLiveTime +
                ", maxAttempts=" + maxAttempts +
                '}';
    }
}
