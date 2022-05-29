/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 2.2
 Description: Main model of the program, responsible for keeping time and references to all other models.
 */

package clock;

import java.util.Calendar;
import java.util.Observable;

public class Model extends Observable {

    Calendar date;

    int hour = 0;
    int minute = 0;
    int second = 0;
    
    int oldSecond = 0;
    int oldMinute = 0;
    int oldHour = 0;

    FunctionMenuModel functionMenuModel;
    AlarmFormModel alarmFormModel;
    AlarmQueueModel alarmQueueModel;
    EditAlarmModel editAlarmModel;
    AlarmingModel alarmingModel;
    HelpModel helpModel;
    SaveModel saveModel;
    LoadModel loadModel;

    public Model() throws QueueUnderflowException {
        functionMenuModel = new FunctionMenuModel();
        alarmFormModel = new AlarmFormModel();
        alarmQueueModel = new AlarmQueueModel<>();
        update();
    }

    /**
     * Checks current time against the last known time if the seconds are different, notify the observers (update the clock face)
     */
    public void update() throws QueueUnderflowException {

        date = Calendar.getInstance();
        oldHour = hour;
        hour = date.get(Calendar.HOUR);
        oldMinute = minute;
        minute = date.get(Calendar.MINUTE);
        oldSecond = second;
        second = date.get(Calendar.SECOND);

        if (oldSecond != second) {
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Checks for any set alarms in the queue.
     *
     *  If queue is empty, return null
     *  If not set, return int 0
     *  If set but current time != alarm time, return int 1
     *  If set and current time == alarm time, return Alarm alarm
     *
     * @throws QueueUnderflowException
     */
    public Object checkSetAlarms() throws QueueUnderflowException {

        Calendar date = Calendar.getInstance();
        if (!alarmQueueModel.isEmpty()) {
            for (Node node = alarmQueueModel.headNode(); node != null; node = node.getNext()) {

                PriorityItem item = (PriorityItem) node.getItem();

                /*CHECK ALARM IS SET
                    (Not implemented) Check day
                    Check hour
                    Check minute
                */
                if (((Alarm)item.getItem()).isSet()) {

                    String hourString = Integer.toString(hour);
                    if (hour < 10) {
                        hourString = "0" + hourString;

                        if (hour == 0) {
                            hourString = "12";
                        }
                    }

                    String minuteString = Integer.toString(minute);
                    if (minute < 10) {
                        minuteString = "0" + minuteString;

                    }

                    if (((Alarm) item.getItem()).getSetTime().substring(0, 2).equals(hourString))  {

                        if (((Alarm) item.getItem()).getSetTime().substring(2, 4).equals(minuteString)) {

                            return item.getItem();
                        }
                    }

                    return  1;

                } else return 0;
            }
        }
        return null;
    }

    /**
     * Converts a Calendar.DAY_OF_WEEK to its binary representation. 1 - 64 = Monday - Sunday
     * @param calendarDay - int representation of the day of the week. 1 - 7 = Sunday - Monday, as dictated by Calendar.DAY_OF_WEEK
     * @return - int of the binary value of the day of the week
     */
    private int toBinaryDay(int calendarDay) {
        switch (calendarDay) {

            //Sunday
            case 1: {return 64;}

            //Monday
            case 2: {return 1;}

            //Tuesday
            case 3: {return 2;}

            //Wednesday
            case 4: {return 4;}

            //Thursday
            case 5: {return 8;}

            //Friday
            case 6: {return 16;}

            //Saturday
            case 7: {return 32;}

            default: return 0;
        }
    }

    /**
     * @return - Model for the function button menu view
     */
    public FunctionMenuModel getFunctionMenuModel() {
        return this.functionMenuModel;
    }

    /**
     * @return - Model for the alarm creation view
     */
    public AlarmFormModel getAlarmFormModel() {
        return this.alarmFormModel;
    }

    /**
     * @return - Model for the alarm list view
     */
    public AlarmQueueModel getAlarmQueueModel() {
        return alarmQueueModel;
    }

    /**
     * Sets the function menu model
     * @param functionMenuModel - Model for the menu button view
     */
    public void setFunctionMenuModel(FunctionMenuModel functionMenuModel) {
        this.functionMenuModel = functionMenuModel;
    }

    /**
     * Sets the alarm queue model
     * @param alarmQueueModel - Model for the alarm list view
     */
    public void setAlarmQueueModel(AlarmQueueModel<Object> alarmQueueModel) {
        this.alarmQueueModel = alarmQueueModel;
    }

    /**
     * @return - Model for the edit alarm view
     */
    public EditAlarmModel getEditAlarmModel() {
        return editAlarmModel;
    }

    /**
     * Set the model for the edit alarm view
     * @param editAlarmModel - Model for the edit alarm view
     */
    public void setEditAlarmModel(EditAlarmModel editAlarmModel) {
        this.editAlarmModel = editAlarmModel;
    }

    /**
     * @return - The model for the ringing alarm view
     */
    public AlarmingModel getAlarmingModel() {
        return alarmingModel;
    }

    /**
     * Set the model for the ringing alarm view
     * @param alarmingModel - Model for the ringing alarm
     */
    public void setAlarmingModel(AlarmingModel alarmingModel) {
        this.alarmingModel = alarmingModel;
    }

    /**
     * Calculates the current time as minutes
     * @return - int minute equivalent of the current time
     */
    public int currentTimeMins() {
        int currentMins;

        currentMins = ((hour * 60) + minute);

        return currentMins;
    }

    /**
     * @return - Model for the help message pop up
     */
    public HelpModel getHelpModel() {
        return helpModel;
    }

    /**
     * Set the model for the help pop up view
     * @param helpModel - Model for the help pop up
     */
    public void setHelpModel(HelpModel helpModel) {
        this.helpModel = helpModel;
    }

    /**
     * Set the model for the save alarm view
     * @param saveModel - Model for the save alarm message
     */
    public void setSaveModel(SaveModel saveModel) {
        this.saveModel = saveModel;
    }

    /**
     * @return - Model for the save alarm view
     */
    public SaveModel getSaveModel() {
        return saveModel;
    }

    /**
     * @return - Model for the load alarm view
     */
    public LoadModel getLoadModel() {
        return loadModel;
    }

    /**
     * Set the model for the load alarm view
     * @param loadModel - Model for the load alarm view
     */
    public void setLoadModel(LoadModel loadModel) {
        this.loadModel = loadModel;
    }
}