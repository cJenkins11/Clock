package clock;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class FunctionMenuView extends JPanel implements Observer {

    FunctionMenuModel model;
    JButton newAlarm;
    JButton checkAlarm;

    public FunctionMenuView(FunctionMenuModel functionMenuModel) {
        model = functionMenuModel;

        setPreferredSize(new Dimension(model.getPREF_WIDTH(), model.PREF_HEIGHT));
        setBackground(model.PREF_BACKGROUND);

        newAlarm = new JButton("New Alarm");

        checkAlarm = new JButton("Check alarm components");

        /*ActionListener createAlarm = e -> {
            AlarmFormView alarmFormView = new AlarmFormView(new AlarmFormModel());
        };
        newAlarm.addActionListener(createAlarm);*/

        setLayout(new GridLayout(5, 1));

        add(newAlarm);
        //add(checkAlarm);

        setVisible(true);
    }

    public JButton getNewAlarm() {
        return newAlarm;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    /*public void setNewAlarmListener(ActionListener newAlarm) {
        this.newAlarm.addActionListener(newAlarm);
    }*/
}
