package clock;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Observable;

public class EditAlarmView extends JFrame {

    EditAlarmModel model;


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
    private JButton updateButton;

    private JPanel alarmPanel;
    private JPanel timePanel;
    private JPanel daysPanel;
    private JPanel namePanel;
    private JPanel buttonPanel;
    int daysRepeating = 0;

    public EditAlarmView(EditAlarmModel editAlarmModel) {
        model = editAlarmModel;

        setPreferredSize(new Dimension(600, 250));

        setBackground(model.PREF_BACKGROUND);

        timePanel = new JPanel();
        timePanel.setPreferredSize(new Dimension(400, 50));
        timePanel.setLayout(new FlowLayout());

        daysPanel = new JPanel();
        daysPanel.setPreferredSize(new Dimension(550, 50));
        daysPanel.setLayout(new GridLayout(1, 7));

        namePanel = new JPanel();
        namePanel.setPreferredSize(new Dimension(400, 50));

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(400, 50));


        hourSpinner = new JSpinner();
        hourSpinner.setPreferredSize(new Dimension(100, 60));


        minuteSpinner = new JSpinner();
        am_pm = new JComboBox();

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

        alarmName = new JTextField();
        JLabel alarmLabel = new JLabel("Alarm name: ");
        alarmName.setPreferredSize(new Dimension(400, 30));


        cancelButton = new JButton("Cancel");
        updateButton = new JButton("Update");

        hourSpinner.setValue(1);
        hourSpinner.setPreferredSize(new Dimension(100,30));
        minuteSpinner.setPreferredSize(new Dimension(100,30));


        am_pm.addItem("AM");
        am_pm.addItem("PM");


        hourSpinner.setValue(Integer.parseInt(model.getAlarm().setTime.substring(0,2)));
        System.out.println("Hours: " + Integer.parseInt(model.getAlarm().setTime.substring(0,2)));

        minuteSpinner.setValue(Integer.parseInt(model.getAlarm().setTime.substring(2,4)));
        System.out.println("Mins: " + Integer.parseInt(model.getAlarm().setTime.substring(2,4)));

        am_pm.setSelectedItem(model.getAlarm().getAM_PM());
        alarmName.setText(model.getAlarm().getAlarmName());


        timePanel.setLayout(new FlowLayout());
        timePanel.add(hourSpinner);
        timePanel.add(minuteSpinner);
        timePanel.add(am_pm);

        daysPanel.add(mondayRB);
        daysPanel.add(tuesdayRB);
        daysPanel.add(wednesdayRB);
        daysPanel.add(thursdayRB);
        daysPanel.add(fridayRB);
        daysPanel.add(saturdayRB);
        daysPanel.add(sundayRB);

        namePanel.setLayout(new FlowLayout());
        namePanel.add(alarmLabel);
        namePanel.add(alarmName);

        buttonPanel.add(cancelButton);
        buttonPanel.add(updateButton);

        alarmPanel = new JPanel();
        //alarmPanel.setPreferredSize(new Dimension(500, 230));


        alarmPanel.add(timePanel, BorderLayout.LINE_START);
        alarmPanel.add(daysPanel);
        alarmPanel.add(namePanel);
        alarmPanel.add(buttonPanel);

        add(alarmPanel);
        //this.setLayout(new GridLayout(4, 1));



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


        ActionListener cancel = e -> {
            dispose();
        };
        cancelButton.addActionListener(cancel);


        pack();
        setVisible(true);
    }

    public EditAlarmModel getModel() {
        return model;
    }

    public void setModel(EditAlarmModel model) {
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

    public JButton getUpdateButton() {
        return updateButton;
    }

    public void setUpdateButton(JButton updateButton) {
        this.updateButton = updateButton;
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

}
