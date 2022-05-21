package clock;

import net.fortuna.ical4j.data.ParserException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Calendar;

public class Controller {
    
    ActionListener listener;
    Timer timer;
    Timer alarmTimer;

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

                    /*if (!model.getAlarmQueueModel().isEmpty()) {
                        updatePriorities();
                        view.getAlarmQueueView().updateQueue();
                        updateAlarmListeners();
                    }*/
                } catch (QueueUnderflowException ex) {
                    ex.printStackTrace();
                }
            }
        };
        
        timer = new Timer(100, listener);
        timer.start();

        WindowListener windowListener = new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                model.getAlarmingModel().getAlarmRinging().setSet(false);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                view.getAlarmingView().getClip().stop();
                view.getAlarmingView().dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                /*try {
                    view.getAlarmQueueView().updateQueue();
                    view.getAlarmQueueView().validate();
                } catch (QueueUnderflowException ex) {
                    ex.printStackTrace();
                }*/
                try {
                    updateAlarmListeners();
                } catch (QueueUnderflowException ex) {
                    ex.printStackTrace();
                }
            }
        };

        ActionListener checkAlarms = new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                try {
                    if (!model.getAlarmQueueModel().isEmpty()) {
                        updatePriorities();
                        view.getAlarmQueueView().updateQueue();
                        updateAlarmListeners();
                    }
                    if (model.checkSetAlarms() != null) {

                        initAlarmAlert(model.checkSetAlarms());
                        view.getAlarmingView().addWindowListener(windowListener);
                        view.getAlarmQueueView().validate();
                        /*view.getAlarmQueueView().updateQueue();
                        model.update();*/
                    }

                } catch (QueueUnderflowException | UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        };

        alarmTimer = new Timer(1000, checkAlarms);
        alarmTimer.start();


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

            if (model.currentTimeMins() > alarm.getSetTimeMins()) {
                System.out.println("Current time is after alarm time");
                priority = (alarm.getSetTimeMins() + 1440) - model.currentTimeMins();
            } else {

                priority = alarm.getSetTimeMins() - model.currentTimeMins();
            }

            /*if (alarm.getAM_PM() == "PM") {
                priority = priority + 720;
            }
*/
            System.out.println("Priority: " + priority);

            /*if (alarm.getAM_PM() == "PM") {
                priority = Integer.parseInt(alarm.getSetTime()) + 1200;
            } else {
                priority = Integer.parseInt(alarm.getSetTime());
            }*/

            model.getAlarmQueueModel().add(alarm, priority);

            try {

                view.getAlarmQueueView().updateQueue();
                //view.getAlarmQueueView().repaint();
                updateAlarmListeners();

            } catch (QueueUnderflowException ex) {
                ex.printStackTrace();
            }

            /*try {
                view.getAlarmQueueView().updateQueue();
                //view.getAlarmQueueView().validate();
            } catch (QueueUnderflowException ex) {
                ex.printStackTrace();
            }*/

            view.getAlarmFormView().dispose();
        };


        ActionListener newAlarm  = e -> {
            view.setAlarmFormView(new AlarmFormView(model.getAlarmFormModel()));
            view.getAlarmFormView().getSaveButton().addActionListener(saveAlarm);
        };
        view.getFunctionMenuView().getNewAlarm().addActionListener(newAlarm);






    }

    private void initAlarmAlert(Object o) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        model.setAlarmingModel(new AlarmingModel((Alarm) o));
        view.setAlarmingView(new AlarmingView(model.getAlarmingModel()));

        ActionListener dismissListener = e -> {
            view.getAlarmingView().getClip().stop();
            //revalidate();
            ImageIcon icon = new ImageIcon("assets/bin.png");
            Image image = icon.getImage(); // transform it
            Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icon = new ImageIcon(newimg);

            JLabel message = new JLabel("Do you  want to delete this alarm?");
            message.setFont(new Font("Arial", Font.BOLD, 16));

            int input = JOptionPane.showConfirmDialog(null,
                    message, "Select an Option...",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon);

            // 0=yes, 1=no, 2=cancel
            System.out.println(input);

            if (input == 0) {
                try {
                    model.getAlarmQueueModel().remove(model.getAlarmingModel().getAlarmRinging());

                    view.getAlarmQueueView().updateQueue();
                    updateAlarmListeners();
                } catch (QueueUnderflowException ex) {
                    ex.printStackTrace();
                }

                view.getAlarmingView().dispose();
            } else if (input == 1) {
                try {
                    view.getAlarmQueueView().updateQueue();
                    updateAlarmListeners();
                } catch (QueueUnderflowException ex) {
                    ex.printStackTrace();
                }

                view.getAlarmingView().dispose();
            } else if (input == 2) {
                view.alarmingView.getClip().start();
            }

        };
        view.getAlarmingView().getDismissButton().addActionListener(dismissListener);
        //view.getFrame().add(view.getFunctionMenuView(), BorderLayout.LINE_START);

    }

    private void initFunctionMenu() {
        model.setFunctionMenuModel(new FunctionMenuModel());
        view.setFunctionMenuView(new FunctionMenuView(model.getFunctionMenuModel()));
        view.getFrame().add(view.getFunctionMenuView(), BorderLayout.LINE_START);

        ActionListener exitListener = e -> {
            System.exit(0);
        };
        view.functionMenuView.getExit().addActionListener(exitListener);


        ActionListener helpListener = e -> {
            initHelp();
        };
        view.functionMenuView.getHelp().addActionListener(helpListener);

        ActionListener saveListener = e -> {
            try {
                initSave();
            } catch (QueueUnderflowException ex) {
                ex.printStackTrace();
            } catch (ParserException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
        view.functionMenuView.getSave().addActionListener(saveListener);
    }

    private void initHelp() {
        model.setHelpModel(new HelpModel());
        view.setHelpView(new HelpView(model.getHelpModel()));

    }

    private void initSave() throws QueueUnderflowException, ParserException, IOException {
        if (model.getAlarmQueueModel().head() != null) {

            model.setSaveModel(new SaveModel(model.getAlarmQueueModel().headNode()));
            view.setSaveView(new SaveView(model.getSaveModel()));
            model.getSaveModel().save();
        }
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
                        updatePriorities();
                        view.getAlarmQueueView().updateQueue();
                        updateAlarmListeners();

                    } catch (QueueUnderflowException ex) {
                        ex.printStackTrace();
                    }

                    view.getEditAlarmView().dispose();
                };

                JPanel alarmPanel = view.getAlarmQueueView().getAlarms()[index];

                JButton editButton = (JButton) alarmPanel.getComponent(2);
                JComboBox set = (JComboBox) alarmPanel.getComponent(3);
                JButton delete = (JButton) alarmPanel.getComponent(4);



                //Node finalAlarmNode = alarmNode;
                ActionListener editListener = e -> {
                    initEditAlarm((Alarm) item.getItem());
                    view.getEditAlarmView().getUpdateButton().addActionListener(updateAlarm);

                    System.out.println(e.getSource());
                    //System.out.println(finalAlarmNode.getItem());
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


                ActionListener deleteListener = e -> {

                    System.out.println("Delete clicked");
                    ImageIcon icon = new ImageIcon("assets/warning.png");
                    Image image = icon.getImage(); // transform it
                    Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                    icon = new ImageIcon(newimg);

                    JLabel message = new JLabel("Are you sure you  want to delete this alarm?");
                    message.setFont(new Font("Arial", Font.BOLD, 16));

                    int input = JOptionPane.showConfirmDialog(null,
                            message, "Select an Option...",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon);

                    // 0=yes, 1=no, 2=cancel
                    System.out.println(input);
                    if (input == 0) {
                        try {

                            model.getAlarmQueueModel().remove((Alarm) item.getItem());
                            view.getAlarmQueueView().updateQueue();
                            updateAlarmListeners();

                        } catch (QueueUnderflowException ex) {
                            ex.printStackTrace();
                        }
                    }

                };

                set.addActionListener(setAlarm);
                editButton.addActionListener(editListener);

                delete.addActionListener(deleteListener);

                index++;
            }
        }
    }


    public void updatePriorities() throws QueueUnderflowException {

        for (Node alarmNode = model.getAlarmQueueModel().headNode(); alarmNode != null; alarmNode = alarmNode.getNext()) {

            int priority = 0;
            PriorityItem item = (PriorityItem) alarmNode.getItem();
            System.out.println("Old Priority: " + item.getPriority());

            if (model.currentTimeMins() > ((Alarm)item.getItem()).getSetTimeMins()) {
                System.out.println("Current time is after alarm time");
                priority = (((Alarm)item.getItem()).getSetTimeMins() + 1440) - model.currentTimeMins();
            } else {

                priority = ((Alarm)item.getItem()).getSetTimeMins() - model.currentTimeMins();
            }

            item.setPriority(priority);
            System.out.println("New Priority: " + item.getPriority());
            System.out.println(model.getAlarmQueueModel().head());



        }



    }




}