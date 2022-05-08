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

    public Model() throws QueueUnderflowException {
        functionMenuModel = new FunctionMenuModel();
        alarmFormModel = new AlarmFormModel();
        alarmQueueModel = new AlarmQueueModel<>();
        //editAlarmModel= new EditAlarmModel();
        update();
    }
    
    public void update() throws QueueUnderflowException {

        date = Calendar.getInstance();
        oldHour = hour;
        hour = date.get(Calendar.HOUR);
        oldMinute = minute;
        minute = date.get(Calendar.MINUTE);
        oldSecond = second;
        second = date.get(Calendar.SECOND);

        /*if (minute != oldMinute) {
            //checkSetAlarms();
        }*/


        if (oldSecond != second) {
            setChanged();
            notifyObservers();
        }
    }

    public Object checkSetAlarms() throws QueueUnderflowException {
        //System.out.println("check set alarms test");
        Calendar date = Calendar.getInstance();
        if (!alarmQueueModel.isEmpty()) {
            for (Node node = alarmQueueModel.headNode(); node != null; node = node.getNext()) {


                //System.out.println(node);
                //System.out.println(node.getNext());
                PriorityItem item = (PriorityItem) node.getItem();

                //CHECK ALARM IS SET
                if (((Alarm)item.getItem()).isSet()) {

                    /*if (toBinaryDay(date.DAY_OF_WEEK) == 0) {
                    }*/

                    //If set alarm hour is equal to the current alarm continue checking
/*
                    System.out.println(date.get(Calendar.HOUR));
                    System.out.println(((Alarm) item.getItem()).getSetTime().substring(0, 2));

                    System.out.println(date.get(Calendar.MINUTE));
                    System.out.println(((Alarm) item.getItem()).getSetTime().substring(2, 4));
*/

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

                    //if (((Alarm) item.getItem()).getSetTime().substring(0, 2).equals(Integer.toString(date.get(hour))))  {
                    if (((Alarm) item.getItem()).getSetTime().substring(0, 2).equals(hourString))  {

                        //System.out.println("Alarm is set for this hour");

                        //if (((Alarm) item.getItem()).getSetTime().substring(2, 4).equals(Integer.toString(date.get(Calendar.MINUTE)))) {
                        if (((Alarm) item.getItem()).getSetTime().substring(2, 4).equals(minuteString)) {

                            //System.out.println("Alarm is set for this minute");
                            //notifyObservers(item.getItem());
                            return item.getItem();
                        }
                    }

                    //System.out.println("Ayo there's a set alarm in here");

                } else {
                    System.out.println("No alarms set");
                }



                //System.out.println("TIME TEST");
                //System.out.println((((Alarm) item.getItem()).getSetTime()));

                //System.out.println((Alarm) item.getItem());
            }
        } else {
            //System.out.println("The queue is empty");
        }
        return null;
    }

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

    public FunctionMenuModel getFunctionMenuModel() {
        return this.functionMenuModel;
    }

    public AlarmFormModel getAlarmFormModel() {
        return this.alarmFormModel;
    }

    public AlarmQueueModel getAlarmQueueModel() {
        return alarmQueueModel;
    }

    public void setFunctionMenuModel(FunctionMenuModel functionMenuModel) {
        this.functionMenuModel = functionMenuModel;
    }

    public void setAlarmQueueModel(AlarmQueueModel<Object> alarmQueueModel) {
        this.alarmQueueModel = alarmQueueModel;
    }

    public EditAlarmModel getEditAlarmModel() {
        return editAlarmModel;
    }

    public void setEditAlarmModel(EditAlarmModel editAlarmModel) {
        this.editAlarmModel = editAlarmModel;
    }

    public AlarmingModel getAlarmingModel() {
        return alarmingModel;
    }

    public void setAlarmingModel(AlarmingModel alarmingModel) {
        this.alarmingModel = alarmingModel;
    }

    public int currentTimeMins() {
        int currentMins = 0;

        //if (Calendar.AM_PM == 0) {
            currentMins = ((hour * 60) + minute);

        /*} else if (Calendar.AM_PM == 1) {
            currentMins = (((hour * 60) - 720) + minute);
        }*/
        //System.out.println(currentMins);
        return currentMins;
    }
}