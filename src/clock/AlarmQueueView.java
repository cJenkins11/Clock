package clock;

import javax.swing.*;
import java.awt.*;

public class AlarmQueueView extends JPanel {

    AlarmQueueModel model;
    JPanel[] alarms;

    public AlarmQueueView(AlarmQueueModel m) throws QueueUnderflowException {
        model = m;

        setPreferredSize(model.PREF_SIZE);
        setBackground(model.PREF_COLOUR);

        if (model.isEmpty()) {
            JLabel message = new JLabel("No alarms set");
            add(message);
        }

    }

    public void updateQueue() throws QueueUnderflowException {
        removeAll();
        //repaint();

        int index = 0;

        alarms = new JPanel[model.getSize()];

        if (model.isEmpty()) {
            JLabel message = new JLabel("No alarms set");
            add(message);
        } else {


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

                //alarmBox.validate();
                //repaint();
                alarmBox.setVisible(true);

                alarms[index] = alarmBox;

                index += 1;
                System.out.println("Index: " + index);
                //System.out.println((Alarm) item.getItem());
            }

        }

        /*for (Node node = model.headNode(); node != null; node = node.getNext()) {

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

            //alarmBox.validate();
            //repaint();
            alarmBox.setVisible(true);

            alarms[index] = alarmBox;

            index += 1;
            System.out.println("Index: " + index);
            //System.out.println((Alarm) item.getItem());
        }*/

        validate();

    }

    public JPanel[] getAlarms() {
        return alarms;
    }

}
