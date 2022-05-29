/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.2
 Description: View to display the queue of set alarms to the user
 */

package clock;

import javax.swing.*;
import java.awt.*;

public class AlarmQueueView extends JPanel {

    AlarmQueueModel model;
    JPanel[] alarms;

    /**
     * Alarm queue view constructor
     * @param m - Model for the view
     */
    public AlarmQueueView(AlarmQueueModel m) {
        model = m;

        setPreferredSize(model.PREF_SIZE);
        setBackground(model.PREF_COLOUR);

        if (model.isEmpty()) {
            JLabel message = new JLabel("No alarms set");
            add(message);
        }
    }

    /**
     * Updates the queue view for additions, removals, and edits
     * @throws QueueUnderflowException
     */
    public void updateQueue() throws QueueUnderflowException {
        removeAll();

        int index = 0;

        alarms = new JPanel[model.getSize()];

        if (model.isEmpty()) {
            JLabel message = new JLabel("No alarms set");
            add(message);
        } else {


            /*
                For each node, create a display for the Alarm in the node
             */
            for (Node node = model.headNode(); node != null; node = node.getNext()) {

                PriorityItem item = (PriorityItem) node.getItem();

                JPanel alarmBox = new JPanel();
                Label alarmName = new Label(((Alarm)item.getItem()).getAlarmName());
                Label alarmTime = new Label(((Alarm) item.getItem()).getFormattedSetTime());

                JButton edit = new JButton("Edit");

                JComboBox alarmOn = new JComboBox();
                alarmOn.addItem("On");
                alarmOn.addItem("Off");

                JButton delete = new JButton("X");

                if (((Alarm) item.getItem()).isSet()) {
                    alarmOn.setSelectedItem("On");
                } else {
                    alarmOn.setSelectedItem("Off");
                }

                alarmBox.add(alarmName);
                alarmBox.add(alarmTime);
                alarmBox.add(edit);
                alarmBox.add(alarmOn);

                alarmBox.add(delete);

                add(alarmBox);

                alarmBox.setVisible(true);

                //Add alarm display to the array
                alarms[index] = alarmBox;

                index += 1;
            }
        }
        validate();
    }

    /**
     * @return - Array of alarm JPanels
     */
    public JPanel[] getAlarms() {
        return alarms;
    }

}
