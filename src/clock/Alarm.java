package clock;

public class Alarm {

    String setTime;
    String AM_PM;
    String alarmName;
    int setDays;
    boolean set;

    /**
     * Alarm object
     *
     * @param setTime - time alarm is set to notify user. 4 digit String, 2 for hours, 2 for minutes. ie 1145, 0345, 1105
     * @param AM_PM  - AM or PM
     * @param alarmName - alarm name, optional
     * @param setDays - repeats on these days. Monday through sunday represented as binary. mon - 1, tue - 2, wed - 4, thu - 8, fri - 16, sat - 32, sun - 64, so timers for the weekdays can be represented as 31 etc
     */
    public Alarm(String setTime, String AM_PM, String alarmName, int setDays) {

        this.setTime = setTime;
        this.AM_PM = AM_PM;
        this.alarmName = alarmName;
        this.setDays = setDays;
        this.set = true;

    }

    public String getSetTime() {
        return setTime;
    }
    public String getFormattedSetTime() {
        return setTime.substring(0, 2) + " : " + setTime.substring(2, 4) + getAM_PM();
    }

    public void setSetTime(String setTime) {
        this.setTime = setTime;
    }

    public String getAM_PM() {
        return AM_PM;
    }

    public void setAM_PM(String AM_PM) {
        this.AM_PM = AM_PM;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public int getSetDays() {
        return setDays;
    }

    public void setSetDays(int setDays) {
        this.setDays = setDays;
    }

    public boolean isSet() {
        return set;
    }

    public void setSet(boolean set) {
        this.set = set;
    }

    @Override
    public String toString() {
        String result = "";

        result = "Time: " + getFormattedSetTime() + "\n" +
                 "Name: " + getAlarmName() + "\n" +
                 "Repeat: " + getSetDays();

        return result;
    }

}
