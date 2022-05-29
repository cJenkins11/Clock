/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.2
 Description: Alarm class, contains information like set time, name, and days it repeats
 */
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

    /**
     * @return - set time as a string
     */
    public String getSetTime() {
        return setTime;
    }

    /**
     * @return - full time string, formatted to 00:00 AM
     */
    public String getFormattedSetTime() {
        return setTime.substring(0, 2) + " : " + setTime.substring(2, 4) + getAM_PM();
    }

    /**
     * @return - int of the minute equivalent of the set time
     */
    public int getSetTimeMins() {
        int mins = 0;
        Integer hours = Integer.parseInt(setTime.substring(0,2));
        Integer minutes = Integer.parseInt(setTime.substring(2,4));

        if (getAM_PM() == "AM") {
            hours += 12;
        }

        mins = (60 * hours) + minutes;

        return mins;
    }

    /**
     * @return - int of the set hour
     */
    public int getHour() {
        Integer hours = Integer.parseInt(setTime.substring(0,2));
        return hours;

    }

    /**
     * @return - int of the set minute
     */
    public int getMinute() {
        Integer minutes = Integer.parseInt(setTime.substring(2,4));
        return minutes;
    }

    /**
     * Sets the alarm time
     * @param setTime - time to set the alarm to
     */
    public void setSetTime(String setTime) {
        this.setTime = setTime;
    }

    /**
     * @return - String, "AM" or "PM"
     */
    public String getAM_PM() {
        return AM_PM;
    }

    /**
     * Set AM/PM
     * @param AM_PM - String, "AM", "PM"
     */
    public void setAM_PM(String AM_PM) {
        this.AM_PM = AM_PM;
    }

    /**
     * @return - String of alarm name
     */
    public String getAlarmName() {
        return alarmName;
    }

    /**
     * Set alarm name
     * @param alarmName - String of new alarm name
     */
    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    /**
     * @return - int representation of the days the alarm repeats
     */
    public int getSetDays() {
        return setDays;
    }

    /**
     * Set days alarm repeats
     * @param setDays - int representation of the days the alarm repeats
     */
    public void setSetDays(int setDays) {
        this.setDays = setDays;
    }

    /**
     * @return - boolean that indicates if the alarm is on/off
     */
    public boolean isSet() {
        return set;
    }

    /**
     * Set if alarm is on/off
     * @param set - boolean that indicates if the alarm is on/off
     */
    public void setSet(boolean set) {
        this.set = set;
    }

    /**
     * @return - Alarm as a string
     */
    @Override
    public String toString() {
        String result = "";

        result = "Time: " + getFormattedSetTime() + "\n" +
                 "Name: " + getAlarmName() + "\n" +
                 "Repeat: " + getSetDays();

        return result;
    }

}
