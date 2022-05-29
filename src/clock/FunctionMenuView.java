/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.2
 Description: View of the function buttons on the main panel
 */
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

    /**
     * Main user interface, has buttons for the functions of the alarm clock
     * @param functionMenuModel
     */
    public FunctionMenuView(FunctionMenuModel functionMenuModel) {
        model = functionMenuModel;

        setPreferredSize(new Dimension(model.getPREF_WIDTH(), model.getPREF_HEIGHT()));
        setBackground(model.getPREF_BACKGROUND());

        newAlarm = new JButton("New Alarm");
        saveAlarms = new JButton("Save Alarms");
        loadAlarms = new JButton("Load Alarms");
        help = new JButton("Help");
        exit = new JButton("Exit");

        setLayout(new GridLayout(5, 1));

        add(newAlarm);
        add(saveAlarms);
        add(loadAlarms);
        add(help);
        add(exit);

        setVisible(true);
    }

    public JButton getNewAlarm() {
        return newAlarm;
    }

    @Override
    public void update(Observable o, Object arg) {}

    public JButton getExit() {
        return exit;
    }

    public JButton getHelp() {
        return help;
    }

    public JButton getSave() {
        return saveAlarms;
    }

    public JButton getLoad() {
        return loadAlarms;
    }
}
