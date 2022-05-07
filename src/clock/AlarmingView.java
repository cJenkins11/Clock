package clock;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;

public class AlarmingView extends JFrame {

    AlarmingModel model;
    JPanel main;

    JLabel alarmName;
    JLabel alarmTime;

    JButton snooze;
    JButton dismiss;

    public AlarmingView(AlarmingModel alarmingModel) {
        model = alarmingModel;

        setPreferredSize(new Dimension(model.getPREF_WIDTH(), model.getPREF_HEIGHT()));
        setBackground(model.PREF_BACKGROUND);
        setTitle("Alarm! Alarm! Alarm!");

        alarmName = new JLabel();
        alarmTime = new JLabel();

        snooze = new JButton("Snooze");
        dismiss = new JButton("Dismiss");



        /*ActionListener createAlarm = e -> {
            AlarmFormView alarmFormView = new AlarmFormView(new AlarmFormModel());
        };
        newAlarm.addActionListener(createAlarm);*/

        //add();
        //add(checkAlarm);

        setVisible(true);
    }

    /*public JButton getNewAlarm() {
        return newAlarm;
    }*/

}
