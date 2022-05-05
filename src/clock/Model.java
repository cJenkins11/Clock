package clock;

import java.util.Calendar;
import java.util.Observable;
//import java.util.GregorianCalendar;

public class Model extends Observable {
    
    int hour = 0;
    int minute = 0;
    int second = 0;
    
    int oldSecond = 0;
    int oldMinute = 0;
    int oldHour = 0;

    FunctionMenuModel functionMenuModel;
    AlarmFormModel alarmFormModel;
    AlarmQueueModel alarmQueueModel;
    
    public Model() throws QueueUnderflowException {
        functionMenuModel = new FunctionMenuModel();
        alarmFormModel = new AlarmFormModel();
        alarmQueueModel = new AlarmQueueModel<>();
        update();
    }
    
    public void update() throws QueueUnderflowException {

        Calendar date = Calendar.getInstance();
        oldHour = hour;
        hour = date.get(Calendar.HOUR);
        oldMinute = minute;
        minute = date.get(Calendar.MINUTE);
        oldSecond = second;
        second = date.get(Calendar.SECOND);

        if (minute != oldMinute) {
            checkSetAlarms();
        }

        /*if (!alarmQueueModel.isEmpty()) {
            for (Node node = alarmQueueModel.headNode(); node != null; node = node.getNext()) {

                System.out.println(node);
                System.out.println(node.getNext());
                PriorityItem item = (PriorityItem) node.getItem();

                System.out.println("TIME TEST");
                System.out.println((((Alarm) item.getItem()).getSetTime()));

                //System.out.println((Alarm) item.getItem());
            }
        }*/


        if (oldSecond != second) {
            setChanged();
            notifyObservers();
        }
    }

    private void checkSetAlarms() throws QueueUnderflowException {
        System.out.println("check set alarms test");
        Calendar date = Calendar.getInstance();
        if (!alarmQueueModel.isEmpty()) {
            for (Node node = alarmQueueModel.headNode(); node != null; node = node.getNext()) {


                System.out.println(node);
                System.out.println(node.getNext());
                PriorityItem item = (PriorityItem) node.getItem();

                //CHECK ALARM IS SET
                if (((Alarm)item.getItem()).isSet()) {

                    if (toBinaryDay(date.DAY_OF_WEEK) == 0) {

                    }
                    System.out.println("Ayo there's a set alarm in here");

                } else {
                    System.out.println("No alarms set");
                }



                System.out.println("TIME TEST");
                System.out.println((((Alarm) item.getItem()).getSetTime()));

                //System.out.println((Alarm) item.getItem());
            }
        } else {
            System.out.println("The queue is empty");
        }
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
}