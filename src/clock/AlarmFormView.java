/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.3
 Description: Alarm creation form view
 */

package clock;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Observable;
import java.util.Observer;

public class AlarmFormView extends JFrame implements Observer {

    AlarmFormModel model;

    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JComboBox am_pm;

    private JRadioButton mondayRB;
    private JRadioButton wednesdayRB;
    private JRadioButton thursdayRB;
    private JRadioButton saturdayRB;
    private JRadioButton sundayRB;
    private JRadioButton tuesdayRB;
    private JRadioButton fridayRB;

    private JTextField alarmName;

    private JButton cancelButton;
    private JButton saveButton;

    private JPanel alarmPanel;
    private JPanel timePanel;
    private JPanel daysPanel;
    private JPanel namePanel;
    private JPanel buttonPanel;

    int daysRepeating = 0;

    /**
     * New view for alarm creation window
     * @param alarmFormModel - Model of the alarm creation form
     */
    public AlarmFormView(AlarmFormModel alarmFormModel) {
        model = alarmFormModel;
        setPreferredSize(new Dimension(model.getPREF_WIDTH(), model.getPREF_HEIGHT()));
        setBackground(model.getPREF_BACKGROUND());

        //Panel for setting the time.
        //JSpinners, combo box and labels
        timePanel = new JPanel();
        timePanel.setPreferredSize(new Dimension(400, 50));
        timePanel.setLayout(new FlowLayout());


        //Panel for selecting days to repeat alarm
        //Grid layout, contains radio buttons
        daysPanel = new JPanel();
        daysPanel.setPreferredSize(new Dimension(550, 50));
        daysPanel.setLayout(new GridLayout(1, 7));


        //Panel for naming the alarm
        namePanel = new JPanel();
        namePanel.setPreferredSize(new Dimension(400, 50));

        //Panel for the cancel/save buttons
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(400, 50));


        //Componenets being added to the panels

        //Time
        hourSpinner = new JSpinner();
        hourSpinner.setPreferredSize(new Dimension(100,30));
        hourSpinner.setValue(1);

        minuteSpinner = new JSpinner();
        minuteSpinner.setPreferredSize(new Dimension(100,30));

        am_pm = new JComboBox();
        am_pm.addItem("AM");
        am_pm.addItem("PM");

        timePanel.setLayout(new FlowLayout());
        timePanel.add(hourSpinner);
        timePanel.add(minuteSpinner);
        timePanel.add(am_pm);


        //Days
        mondayRB = new JRadioButton();
        mondayRB.setText("Monday");

        tuesdayRB = new JRadioButton();
        tuesdayRB.setText("Tuesday");

        wednesdayRB = new JRadioButton();
        wednesdayRB.setText("Wednesday");

        thursdayRB = new JRadioButton();
        thursdayRB.setText("Thursday");

        fridayRB = new JRadioButton();
        fridayRB.setText("Friday");

        saturdayRB = new JRadioButton();
        saturdayRB.setText("Saturday");

        sundayRB = new JRadioButton();
        sundayRB.setText("Sunday");

        daysPanel.add(mondayRB);
        daysPanel.add(tuesdayRB);
        daysPanel.add(wednesdayRB);
        daysPanel.add(thursdayRB);
        daysPanel.add(fridayRB);
        daysPanel.add(saturdayRB);
        daysPanel.add(sundayRB);


        //Name
        alarmName = new JTextField();
        JLabel alarmLabel = new JLabel("Alarm name: ");
        alarmName.setPreferredSize(new Dimension(400, 30));

        namePanel.setLayout(new FlowLayout());
        namePanel.add(alarmLabel);
        namePanel.add(alarmName);


        //Buttons
        cancelButton = new JButton("Cancel");
        saveButton = new JButton("Save");

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);


        //Main panel
        alarmPanel = new JPanel();

        alarmPanel.add(timePanel, BorderLayout.LINE_START);
        alarmPanel.add(daysPanel);
        alarmPanel.add(namePanel);
        alarmPanel.add(buttonPanel);

        add(alarmPanel);


        /*
            Listens to the hour spinner:
                Change combobox to AM/PM when looping from 12 -> 1 or 1 -> 12
                Prevent values of <1 and >12 being entered in the spinners

        */
        ChangeListener hourListener = e -> {

            switch (hourSpinner.getValue().toString()) {

                case "12": {

                    if (am_pm.getSelectedItem().equals("AM")) {
                        am_pm.getModel().setSelectedItem("PM");

                    } else {
                        am_pm.setSelectedItem("AM");
                    }
                    break;
                }

                case "0": {
                    hourSpinner.setValue(12);
                    break;
                }

                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                case "10":
                case "11": {
                    break;
                }

                default: {
                    hourSpinner.setValue(1);
                    break;
                }
            }
        };
        hourSpinner.addChangeListener(hourListener);


        /*
            Listens to the minute spinner:
                Prevent values of <0 and >59 being entered in the spinners
        */
        ChangeListener minuteListener = e -> {

            if (minuteSpinner.getValue().equals(60)) {
                minuteSpinner.setValue(0);
            }
            if (minuteSpinner.getValue().equals(-1)) {
                minuteSpinner.setValue(59);
            }
            if (Integer.parseInt(minuteSpinner.getValue().toString()) < -1 || Integer.parseInt(minuteSpinner.getValue().toString()) > 60) {
                minuteSpinner.setValue(1);
            }

        };
        minuteSpinner.getModel().addChangeListener(minuteListener);


        /*
            Listens to the radio buttons for mon-sun:
                If checked, add int value of day to daysRepeating
                If unchecked, subtract int value of day to daysRepeating
        */
        ItemListener mondayListener = e -> {
            System.out.println("MONDAY");
            if (mondayRB.isSelected()) {
                daysRepeating += 1;
            } else {
                daysRepeating -= 1;
                System.out.println("Not selected");
            }
            System.out.println(daysRepeating);
        };

        ItemListener tuesdayListener = e -> {
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
        };

        mondayRB.addItemListener(mondayListener);
        tuesdayRB.addItemListener(tuesdayListener);
        wednesdayRB.addItemListener(wednesdayListener);
        thursdayRB.addItemListener(thursdayListener);
        fridayRB.addItemListener(fridayListener);
        saturdayRB.addItemListener(saturdayListener);
        sundayRB.addItemListener(sundayListener);


        //Closes the form frame
        ActionListener cancel = e -> {
            dispose();
        };
        cancelButton.addActionListener(cancel);

        pack();
        setVisible(true);
    }

    public AlarmFormModel getModel() {
        return model;
    }

    public void setModel(AlarmFormModel model) {
        this.model = model;
    }

    public JSpinner getHourSpinner() {
        return hourSpinner;
    }

    public void setHourSpinner(JSpinner hourSpinner) {
        this.hourSpinner = hourSpinner;
    }

    public JSpinner getMinuteSpinner() {
        return minuteSpinner;
    }

    public void setMinuteSpinner(JSpinner minuteSpinner) {
        this.minuteSpinner = minuteSpinner;
    }

    public JComboBox getAm_pm() {
        return am_pm;
    }

    public void setAm_pm(JComboBox am_pm) {
        this.am_pm = am_pm;
    }

    public JRadioButton getMondayRB() {
        return mondayRB;
    }

    public void setMondayRB(JRadioButton mondayRB) {
        this.mondayRB = mondayRB;
    }

    public JRadioButton getWednesdayRB() {
        return wednesdayRB;
    }

    public void setWednesdayRB(JRadioButton wednesdayRB) {
        this.wednesdayRB = wednesdayRB;
    }

    public JRadioButton getThursdayRB() {
        return thursdayRB;
    }

    public void setThursdayRB(JRadioButton thursdayRB) {
        this.thursdayRB = thursdayRB;
    }

    public JRadioButton getSaturdayRB() {
        return saturdayRB;
    }

    public void setSaturdayRB(JRadioButton saturdayRB) {
        this.saturdayRB = saturdayRB;
    }

    public JRadioButton getSundayRB() {
        return sundayRB;
    }

    public void setSundayRB(JRadioButton sundayRB) {
        this.sundayRB = sundayRB;
    }

    public JRadioButton getTuesdayRB() {
        return tuesdayRB;
    }

    public void setTuesdayRB(JRadioButton tuesdayRB) {
        this.tuesdayRB = tuesdayRB;
    }

    public JRadioButton getFridayRB() {
        return fridayRB;
    }

    public void setFridayRB(JRadioButton fridayRB) {
        this.fridayRB = fridayRB;
    }

    public JTextField getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(JTextField alarmName) {
        this.alarmName = alarmName;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(JButton cancelButton) {
        this.cancelButton = cancelButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(JButton saveButton) {
        this.saveButton = saveButton;
    }

    public JPanel getAlarmPanel() {
        return alarmPanel;
    }

    public void setAlarmPanel(JPanel alarmPanel) {
        this.alarmPanel = alarmPanel;
    }

    public int getDaysRepeating() {
        return daysRepeating;
    }

    public void setDaysRepeating(int daysRepeating) {
        this.daysRepeating = daysRepeating;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
