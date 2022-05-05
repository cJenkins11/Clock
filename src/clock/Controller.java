package clock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    
    ActionListener listener;
    Timer timer;
    
    Model model;
    /*AlarmFormModel alarmFormModel;
    AlarmQueueModel alarmQueueModel;
    FunctionMenuModel functionMenuModel;*/

    View view;
    /*AlarmFormView alarmFormView;
    AlarmQueueView alarmQueueView;*/
    //FunctionMenuView functionMenuView;


    public Controller(Model m, View v) throws QueueUnderflowException {
        model = m;
        view = v;

        initFunctionMenu();
        initAlarmQueue();

        /*ActionListener editListener = e -> {
            System.out.println(e.getSource());
            //model.getAlarmQueueModel().editAlarm(e.getSource());
        };*/

        listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    model.update();
                    //updateAlarmListeners();
                } catch (QueueUnderflowException ex) {
                    ex.printStackTrace();
                }
            }


            /*private void updateAlarmListeners() throws QueueUnderflowException {
                if (!model.getAlarmQueueModel().isEmpty()) {
                    int index = 0;
                    for (Node node = model.getAlarmQueueModel().headNode(); node != null; node = node.getNext()) {

                        //PriorityItem item = (PriorityItem) node.getItem();

                        JPanel alarmPanel = view.getAlarmQueueView().getAlarms()[index];
                        JButton editButton = (JButton) alarmPanel.getComponent(2);
                        editButton.addActionListener(editListener);
                        //alarmPanel.getComponentCount()
                        //System.out.println(Arrays.toString(alarmPanel.getComponents()));
                        //System.out.println(alarmPanel.getComponent(5).getClass());
                        //alarmPanel.getComponent(1);
                    }
                }
            }*/
        };
        
        timer = new Timer(100, listener);
        timer.start();

        ActionListener saveAlarm = e -> {

            System.out.println("test");

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

            System.out.println(alarm);
            view.getAlarmFormView().dispose();
        };






        ActionListener newAlarm  = e -> {
            System.out.println("TEST");
            view.setAlarmFormView(new AlarmFormView(model.getAlarmFormModel()));
            view.getAlarmFormView().getSaveButton().addActionListener(saveAlarm);
        };
        view.getFunctionMenuView().getNewAlarm().addActionListener(newAlarm);


        /*ActionListener editListener = e -> {
            System.out.println(e.getSource());
            //model.getAlarmQueueModel().editAlarm(e.getSource());
        };*/

        /*if (!model.getAlarmQueueModel().isEmpty()) {
            int index = 0;
            for (Node node = model.getAlarmQueueModel().headNode(); node != null; node = node.getNext()) {

                PriorityItem item = (PriorityItem) node.getItem();

                JPanel alarmPanel = view.getAlarmQueueView().getAlarms()[index];
                //alarmPanel.getComponentCount()
                System.out.println(Arrays.toString(alarmPanel.getComponents()));
                //System.out.println(alarmPanel.getComponent(5).getClass());
                //alarmPanel.getComponent(1);
            }
        }*/


        /*ActionListener checkAlarm = e -> {
            int index = 0;
            try {
                for (Node node = model.getAlarmQueueModel().headNode(); node != null; node = node.getNext()) {

                    JPanel alarmPanel = view.getAlarmQueueView().getAlarms()[index];
                    //alarmPanel.getComponentCount()
                    System.out.println(Arrays.toString(alarmPanel.getComponents()));
                    index++;
                }
            } catch (QueueUnderflowException ex) {
                ex.printStackTrace();
            }
        };
        view.getFunctionMenuView().checkAlarm.addActionListener(checkAlarm);*/

            //view.getAlarmQueueView();
        //view.functionMenuView.newAlarm.addActionListener(newAlarm);

        /*ActionListener newAlarm = e -> {
            alarmFormView = new AlarmFormView(model.getAlarmFormModel());
            //alarmFormView = new AlarmFormView(alarmFormModel);
            //view.pane.add(alarmFormView);
            alarmFormView.getSaveButton().addActionListener(saveListener);

        };
        view.functionMenuView.setNewAlarmListener(newAlarm);*/
        //functionMenuView.getNewAlarm().addActionListener(newAlarm);

        /*ActionListener button5 = e -> {
            System.out.println("TEST");
        };
        functionMenuView.newAlarm.addActionListener(button5);*/


        /*ItemListener mondayListener = e -> {
            System.out.println("MONDAY");
            if (alarmFormView.getMondayRB().isSelected()) {
                alarmFormModel.daysRepeating += 1;
            } else {
                alarmFormModel.daysRepeating -= 1;
                System.out.println("Not selected");
            }
            System.out.println(alarmFormModel.daysRepeating);
        };*/

        /*ItemListener tuesdayListener = e -> {
            System.out.println("TUESDAY");
            if (tuesdayRB.isSelected()) {
                daysRepeating += 2;
            } else {
                daysRepeating -= 2;
                System.out.println("Not selected");
            }
            System.out.println(daysRepeating);
        };

        ItemListener wednesdayListener = e -> {
            System.out.println("WEDNESDAY");
            if (wednesdayRB.isSelected()) {
                daysRepeating += 4;
            } else {
                daysRepeating -= 4;
                System.out.println("Not selected");
            }
            System.out.println(daysRepeating);
        };

        ItemListener thursdayListener = e -> {
            System.out.println("THURSDAY");
            if (thursdayRB.isSelected()) {
                daysRepeating += 8;
            } else {
                daysRepeating -= 8;
                System.out.println("Not selected");
            }
            System.out.println(daysRepeating);
        };

        ItemListener fridayListener = e -> {
            System.out.println("FRIDAY");
            if (fridayRB.isSelected()) {
                daysRepeating += 16;
            } else {
                daysRepeating -= 16;
                System.out.println("Not selected");
            }
            System.out.println(daysRepeating);
        };

        ItemListener saturdayListener = e -> {
            System.out.println("SATURDAY");
            if (saturdayRB.isSelected()) {
                daysRepeating += 32;
            } else {
                daysRepeating -= 32;
                System.out.println("Not selected");
            }
            System.out.println(daysRepeating);
        };

        ItemListener sundayListener = e -> {
            System.out.println("SUNDAY");
            if (sundayRB.isSelected()) {
                daysRepeating += 64;
            } else {
                daysRepeating -= 64;
                System.out.println("Not selected");
            }
            System.out.println(daysRepeating);
        };*/

        //alarmFormView.getMondayRB().addItemListener(mondayListener);
        /*tuesdayRB.addItemListener(tuesdayListener);
        wednesdayRB.addItemListener(wednesdayListener);
        thursdayRB.addItemListener(thursdayListener);
        fridayRB.addItemListener(fridayListener);
        saturdayRB.addItemListener(saturdayListener);
        sundayRB.addItemListener(sundayListener);*/

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

    private void updateAlarmListeners() throws QueueUnderflowException {
        if (!model.getAlarmQueueModel().isEmpty()) {
            int index = 0;
            for (Node alarmNode = model.getAlarmQueueModel().headNode(); alarmNode != null; alarmNode = alarmNode.getNext()) {

                //PriorityItem item = (PriorityItem) node.getItem();

                JPanel alarmPanel = view.getAlarmQueueView().getAlarms()[index];
                JButton editButton = (JButton) alarmPanel.getComponent(2);
                JComboBox set = (JComboBox) alarmPanel.getComponent(3);

                Node finalAlarmNode = alarmNode;
                ActionListener editListener = e -> {
                    System.out.println(e.getSource());
                    System.out.println(finalAlarmNode.getItem());
                    //model.getAlarmQueueModel().editAlarm(e.getSource());
                };

                ActionListener setAlarm = e -> {
                    System.out.println(set.getSelectedItem());
                };

                set.addActionListener(setAlarm);
                editButton.addActionListener(editListener);
                //alarmPanel.getComponentCount()
                //System.out.println(Arrays.toString(alarmPanel.getComponents()));
                //System.out.println(alarmPanel.getComponent(5).getClass());
                //alarmPanel.getComponent(1);
                index++;
            }
        }
    }
}