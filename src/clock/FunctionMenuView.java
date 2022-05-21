package clock;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class FunctionMenuView extends JPanel implements Observer {

    FunctionMenuModel model;
    JButton newAlarm;
    JButton saveAlarms;
    JButton loadAlarms;
    JButton help;
    JButton exit;

    //Test buttons
    //JButton checkAlarm;
    //JButton checkRemove;

    public FunctionMenuView(FunctionMenuModel functionMenuModel) {
        model = functionMenuModel;

        setPreferredSize(new Dimension(model.getPREF_WIDTH(), model.PREF_HEIGHT));
        setBackground(model.PREF_BACKGROUND);

        newAlarm = new JButton("New Alarm");
        saveAlarms = new JButton("Save Alarms");
        loadAlarms = new JButton("Load Alarms");
        help = new JButton("Help");
        exit = new JButton("Exit");


        //Test items
       // checkAlarm = new JButton("Check alarm components");

        //checkRemove = new JButton("Check remove");
        //
        /*ActionListener createAlarm = e -> {
            AlarmFormView alarmFormView = new AlarmFormView(new AlarmFormModel());
        };
        newAlarm.addActionListener(createAlarm);*/

        setLayout(new GridLayout(5, 1));

        add(newAlarm);
        add(saveAlarms);
        add(loadAlarms);
        add(help);
        add(exit);

        //add(checkRemove);
        //add(checkAlarm);

        setVisible(true);
    }

    public JButton getNewAlarm() {
        return newAlarm;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public JButton getExit() {
        return exit;
    }

    public JButton getHelp() {
        return help;
    }

    public JButton getSave() {
        return saveAlarms;
    }

    /*public void setNewAlarmListener(ActionListener newAlarm) {
        this.newAlarm.addActionListener(newAlarm);
    }*/
}
