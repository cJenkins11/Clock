package clock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    
    ActionListener listener;
    Timer timer;
    
    Model model;

    View view;

    public Controller(Model m, View v) throws QueueUnderflowException {
        model = m;
        view = v;

        initFunctionMenu();
        initAlarmQueue();

        listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    model.update();

                } catch (QueueUnderflowException ex) {
                    ex.printStackTrace();
                }
            }
        };
        
        timer = new Timer(100, listener);
        timer.start();

        ActionListener saveAlarm = e -> {

            String timeString = "";

            if (Integer.parseInt(view.getAlarmFormView().getHourSpinner().getValue().toString()) < 10 ) {
                timeString += "0" + view.getAlarmFormView().getHourSpinner().getValue().toString();
            } else {
                timeString += view.getAlarmFormView().getHourSpinner().getValue().toString();
            }

            if (Integer.parseInt(view.getAlarmFormView().getMinuteSpinner().getValue().toString()) < 10 ) {
                timeString += "0" + view.getAlarmFormView().getMinuteSpinner().getValue().toString();
            } else {
                timeString += view.getAlarmFormView().getMinuteSpinner().getValue().toString();
            }

            System.out.println(timeString);
            int priority;

            Alarm alarm = new Alarm(timeString, view.getAlarmFormView().getAm_pm().getSelectedItem().toString(), view.getAlarmFormView().getAlarmName().getText(), view.getAlarmFormView().getDaysRepeating());

            if (alarm.getAM_PM() == "PM") {
                priority = Integer.parseInt(alarm.getSetTime()) + 1200;
            } else {
                priority = Integer.parseInt(alarm.getSetTime());
            }

            model.getAlarmQueueModel().add(alarm, priority);

            try {

                view.getAlarmQueueView().updateQueue();
                updateAlarmListeners();

            } catch (QueueUnderflowException ex) {
                ex.printStackTrace();
            }

            view.getAlarmFormView().dispose();
        };






        ActionListener newAlarm  = e -> {
            view.setAlarmFormView(new AlarmFormView(model.getAlarmFormModel()));
            view.getAlarmFormView().getSaveButton().addActionListener(saveAlarm);
        };
        view.getFunctionMenuView().getNewAlarm().addActionListener(newAlarm);


    }

    private void initFunctionMenu() {
        model.setFunctionMenuModel(new FunctionMenuModel());
        view.setFunctionMenuView(new FunctionMenuView(model.getFunctionMenuModel()));
        view.getFrame().add(view.getFunctionMenuView(), BorderLayout.LINE_START);
    }

    private void initAlarmQueue() throws QueueUnderflowException {
        model.setAlarmQueueModel(new AlarmQueueModel<>());
        view.setAlarmQueueView(new AlarmQueueView(model.getAlarmQueueModel()));
        view.getFrame().add(view.getAlarmQueueView(), BorderLayout.LINE_END);
    }

    private void initEditAlarm(Alarm alarm) {
        model.setEditAlarmModel(new EditAlarmModel(alarm));
        view.setEditAlarmView(new EditAlarmView(model.getEditAlarmModel()));
    }

    private void updateAlarmListeners() throws QueueUnderflowException {
        if (!model.getAlarmQueueModel().isEmpty()) {
            int index = 0;

            for (Node alarmNode = model.getAlarmQueueModel().headNode(); alarmNode != null; alarmNode = alarmNode.getNext()) {


                PriorityItem item = (PriorityItem) alarmNode.getItem();

                ActionListener updateAlarm = e -> {
                    String hour = Integer.toString((Integer) view.getEditAlarmView().getHourSpinner().getValue());
                    String minute = Integer.toString((Integer) view.getEditAlarmView().getMinuteSpinner().getValue());

                    if (Integer.parseInt(hour) < 10) {
                        hour = "0" + hour;
                    }
                    if (Integer.parseInt(minute) < 10) {
                        minute = "0" + minute;
                    }

                    ((Alarm) item.getItem()).setSetTime(hour + minute);
                    ((Alarm) item.getItem()).setAlarmName(view.getEditAlarmView().getAlarmName().getText());
                    ((Alarm) item.getItem()).setAM_PM(view.getEditAlarmView().getAm_pm().getSelectedItem().toString());

                    if (view.getEditAlarmView().getAm_pm().getSelectedItem().toString() == "PM") {
                        item.setPriority(Integer.parseInt(hour + minute) + 1200);
                        System.out.println("time pm: +1200 " + hour + minute);
                    } else {
                        item.setPriority(Integer.parseInt(hour + minute));
                        System.out.println("AM Time" + hour + minute);
                    }


                    try {
                        view.getAlarmQueueView().updateQueue();
                    } catch (QueueUnderflowException ex) {
                        ex.printStackTrace();
                    }

                    view.getEditAlarmView().dispose();
                };

                JPanel alarmPanel = view.getAlarmQueueView().getAlarms()[index];

                JButton editButton = (JButton) alarmPanel.getComponent(2);
                JComboBox set = (JComboBox) alarmPanel.getComponent(3);



                Node finalAlarmNode = alarmNode;
                ActionListener editListener = e -> {
                    initEditAlarm((Alarm) item.getItem());
                    view.getEditAlarmView().getUpdateButton().addActionListener(updateAlarm);

                    System.out.println(e.getSource());
                    System.out.println(finalAlarmNode.getItem());
                };

                ActionListener setAlarm = e -> {

                    if (set.getSelectedItem() == "Off") {

                        ((Alarm) item.getItem()).setSet(false);
                        System.out.println((Alarm) item.getItem());

                    } else {

                        ((Alarm) item.getItem()).setSet(true);
                        System.out.println((Alarm) item.getItem());

                    }

                    System.out.println(set.getSelectedItem());
                };

                set.addActionListener(setAlarm);
                editButton.addActionListener(editListener);

                index++;
            }
        }
    }
}