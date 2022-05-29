/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 2.2
 Description: Main controller of the program, handles event listeners for the different views
 */

package clock;

import net.fortuna.ical4j.data.ParserException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.text.ParseException;

public class Controller {
    
    ActionListener listener;
    Timer timer;
    Timer alarmTimer;

    Model model;

    View view;

    /**
     * Constructor of the controller of the alarm clock
     * @param m - the main model of the program
     * @param v - the main view of the program
     * @throws QueueUnderflowException
     */
    public Controller(Model m, View v) throws QueueUnderflowException {
        model = m;
        view = v;

        //Side panels of the main view
        initFunctionMenu();
        initAlarmQueue();

        //Timer for updating the time on the  clock face
        listener = e -> {
            try {
                model.update();

            } catch (QueueUnderflowException ex) {
                ex.printStackTrace();
            }
        };
        timer = new Timer(100, listener);
        timer.start();

        /*
            On alarming window opening, set the alarm to be turned off.
            On window closing, stop the sound and dispose the window
            On window closed, update the alarm listeners
         */
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

                try {
                    updateAlarmListeners();
                } catch (QueueUnderflowException ex) {
                    ex.printStackTrace();
                }
            }
        };


        /*
            Checks for any set alarms every second
            If any alarms are set and toggled "On" the alarm time is checked against the current time
         */
        ActionListener checkAlarms = e -> {

            try {
                if (!model.getAlarmQueueModel().isEmpty()) {
                    updatePriorities();
                    view.getAlarmQueueView().updateQueue();
                    updateAlarmListeners();
                }
                if (model.checkSetAlarms() != null) {

                    if ((int) model.checkSetAlarms() == 1 || (int) model.checkSetAlarms() == 0) {

                    } else {
                        initAlarmAlert(model.checkSetAlarms());
                        view.getAlarmingView().addWindowListener(windowListener);
                        view.getAlarmQueueView().validate();
                    }

                }

            } catch (QueueUnderflowException | UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        };
        alarmTimer = new Timer(1000, checkAlarms);
        alarmTimer.start();


        /*
            Save alarm button listener. Creates an alarm from the information in the form
            Add new alarm to the alarm queue
         */
        ActionListener saveAlarm = e -> {

            String timeString = "";

            //Format with leading 0s if needed
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


            int priority;

            Alarm alarm = new Alarm(timeString, view.getAlarmFormView().getAm_pm().getSelectedItem().toString(), view.getAlarmFormView().getAlarmName().getText(), view.getAlarmFormView().getDaysRepeating());

            /*
                Calculate priority of the alarm
                Calculated as the minutes away from the time the alarm is set and the current time
             */
            if (model.currentTimeMins() > alarm.getSetTimeMins()) {
                priority = (alarm.getSetTimeMins() + 1440) - model.currentTimeMins();
            } else {
                priority = alarm.getSetTimeMins() - model.currentTimeMins();
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

        //Initialise the alarm creation form model and view
        ActionListener newAlarm  = e -> {
            view.setAlarmFormView(new AlarmFormView(model.getAlarmFormModel()));
            view.getAlarmFormView().getSaveButton().addActionListener(saveAlarm);
        };
        view.getFunctionMenuView().getNewAlarm().addActionListener(newAlarm);
    }

    /**
     * Inits a display for the user notifying them of their ringing alarm
     * @param o - Currently active alarm
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     * @throws IOException
     */
    public void initAlarmAlert(Object o) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        model.setAlarmingModel(new AlarmingModel((Alarm) o));
        view.setAlarmingView(new AlarmingView(model.getAlarmingModel()));

        /*
            Dismiss alarm listener
                Asks user if they want to remove the alarm after dismissing it.
                If yes, remove the alarm.
                If no, turn the alarm off
         */
        ActionListener dismissListener = e -> {
            view.getAlarmingView().getClip().stop();

            ImageIcon icon = new ImageIcon("assets/bin.png");
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newimg);

            JLabel message = new JLabel("Do you  want to delete this alarm?");
            message.setFont(new Font("Arial", Font.BOLD, 16));

            int input = JOptionPane.showConfirmDialog(null,
                    message, "Select an Option...",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon);

            // 0=yes, 1=no, 2=cancel
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
    }

    /**
     * Init the button menu of the main view
     */
    private void initFunctionMenu() {
        model.setFunctionMenuModel(new FunctionMenuModel());
        view.setFunctionMenuView(new FunctionMenuView(model.getFunctionMenuModel()));
        view.getFrame().add(view.getFunctionMenuView(), BorderLayout.LINE_START);

        //Terminate program on exit button click
        ActionListener exitListener = e -> {
            System.exit(0);
        };
        view.functionMenuView.getExit().addActionListener(exitListener);

        //Display help message
        ActionListener helpListener = e -> {
            initHelp();
        };
        view.functionMenuView.getHelp().addActionListener(helpListener);

        //Save current set alarms
        ActionListener saveListener = e -> {
            try {
                initSave();
            } catch (QueueUnderflowException ex) {
                ex.printStackTrace();
            } catch (ParserException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        };
        view.functionMenuView.getSave().addActionListener(saveListener);


        /*
            Load alarm listener
                Reads mycalendar.ics and creates alarms in the file
         */
        ActionListener loadAlarm = e -> {
            try {
                initLoad();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ParserException ex) {
                ex.printStackTrace();
            }
        };
        view.getFunctionMenuView().getLoad().addActionListener(loadAlarm);

    }

    /**
     * Init help message
     */
    private void initHelp() {
        model.setHelpModel(new HelpModel());
        view.setHelpView(new HelpView(model.getHelpModel()));
    }

    /**
     * Init save functions
     * @throws QueueUnderflowException
     * @throws ParserException
     * @throws IOException
     */
    private void initSave() throws QueueUnderflowException, ParserException, IOException, ParseException {
        if (model.getAlarmQueueModel().head() != null) {

            model.setSaveModel(new SaveModel(model.getAlarmQueueModel().headNode()));
            view.setSaveView(new SaveView(model.getSaveModel()));
            model.getSaveModel().save();
        }
    }

    /**
     * Loads alarms from the mycalendar.ics file
     */
    private void initLoad() throws IOException, ParserException {

        model.setLoadModel(new LoadModel());
        view.setLoadView(new LoadView(model.getLoadModel()));
    }


    /**
     * Init alarm queue on main panel
     * @throws QueueUnderflowException
     */
    private void initAlarmQueue() throws QueueUnderflowException {
        model.setAlarmQueueModel(new AlarmQueueModel<>());
        view.setAlarmQueueView(new AlarmQueueView(model.getAlarmQueueModel()));
        view.getFrame().add(view.getAlarmQueueView(), BorderLayout.LINE_END);
    }

    /**
     * Initialise the edit window
     * @param alarm - the Alarm being edited
     */
    private void initEditAlarm(Alarm alarm) {
        model.setEditAlarmModel(new EditAlarmModel(alarm));
        view.setEditAlarmView(new EditAlarmView(model.getEditAlarmModel()));
    }

    /**
     * Updates the listeners on each alarm display in the queue
     * @throws QueueUnderflowException
     */
    private void updateAlarmListeners() throws QueueUnderflowException {
        if (!model.getAlarmQueueModel().isEmpty()) {
            int index = 0;

            //Get display for each node to add listeners too
            for (Node alarmNode = model.getAlarmQueueModel().headNode(); alarmNode != null; alarmNode = alarmNode.getNext()) {

                PriorityItem item = (PriorityItem) alarmNode.getItem();

                //Format time with leading 0s if needed
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

                    //If time set is PM add 12 hours to the set time
                    if (view.getEditAlarmView().getAm_pm().getSelectedItem().toString() == "PM") {
                        item.setPriority(Integer.parseInt(hour + minute) + 1200);

                    } else {
                        item.setPriority(Integer.parseInt(hour + minute));
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

                //Get the alarm display
                JPanel alarmPanel = view.getAlarmQueueView().getAlarms()[index];

                //Get the components to add listeners to
                JButton editButton = (JButton) alarmPanel.getComponent(2);
                JComboBox set = (JComboBox) alarmPanel.getComponent(3);
                JButton delete = (JButton) alarmPanel.getComponent(4);

                //Initiate edit window of the current alarm
                ActionListener editListener = e -> {
                    initEditAlarm((Alarm) item.getItem());
                    view.getEditAlarmView().getUpdateButton().addActionListener(updateAlarm);
                };

                //Toggle alarm on/off
                ActionListener setAlarm = e -> {
                    if (set.getSelectedItem() == "Off") {
                        ((Alarm) item.getItem()).setSet(false);
                    } else {
                        ((Alarm) item.getItem()).setSet(true);
                    }
                };

                //Display window to allow user to confirm deletion in case of accidental click
                ActionListener deleteListener = e -> {

                    ImageIcon icon = new ImageIcon("assets/warning.png");
                    Image image = icon.getImage();
                    Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newimg);

                    JLabel message = new JLabel("Are you sure you  want to delete this alarm?");
                    message.setFont(new Font("Arial", Font.BOLD, 16));

                    int input = JOptionPane.showConfirmDialog(null,
                            message, "Select an Option...",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon);

                    // 0=yes, 1=no, 2=cancel
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

    /**
     * Every minute priorities get updated to preserve the order of the queue
     * @throws QueueUnderflowException
     */
    public void updatePriorities() throws QueueUnderflowException {

        for (Node alarmNode = model.getAlarmQueueModel().headNode(); alarmNode != null; alarmNode = alarmNode.getNext()) {

            int priority;
            PriorityItem item = (PriorityItem) alarmNode.getItem();

            if (model.currentTimeMins() > ((Alarm)item.getItem()).getSetTimeMins()) {
                priority = (((Alarm)item.getItem()).getSetTimeMins() + 1440) - model.currentTimeMins();
            } else {
                priority = ((Alarm)item.getItem()).getSetTimeMins() - model.currentTimeMins();
            }

            item.setPriority(priority);
        }
    }
}