package clock;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    JFrame frame;
    ClockPanel panel;
    FunctionMenuView functionMenuView;
    AlarmFormView alarmFormView;
    AlarmQueueView alarmQueueView;
    EditAlarmView editAlarmView;
    AlarmingView alarmingView;

    public View(Model model) {
        frame = new JFrame();
        panel = new ClockPanel(model);


        //frame.setContentPane(panel);
        frame.setTitle("Java Clock");
        frame.setPreferredSize(new Dimension(800, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Start of border layout code

        Container pane = frame.getContentPane();
         
        panel.setPreferredSize(new Dimension(400, 400));
        pane.add(panel, BorderLayout.CENTER);

        // End of borderlayout code


        frame.pack();
        frame.setVisible(true);
    }
    
    public void update(Observable o, Object arg) {
        frame.pack();
        panel.repaint();
        if (alarmQueueView.getAlarms() != null) {

            /*try {
                alarmQueueView.updateQueue();
            } catch (QueueUnderflowException e) {
                e.printStackTrace();
            }*/

        }

    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFunctionMenuView(FunctionMenuView functionMenuView) {
        this.functionMenuView = functionMenuView;
    }

    public FunctionMenuView getFunctionMenuView() {
        return functionMenuView;
    }

    public void setAlarmQueueView(AlarmQueueView alarmQueueView) {
        this.alarmQueueView = alarmQueueView;
    }

    public AlarmQueueView getAlarmQueueView() {
        return alarmQueueView;
    }

    public void setAlarmFormView(AlarmFormView alarmFormView) {
        this.alarmFormView = alarmFormView;
    }

    public AlarmFormView getAlarmFormView() {
        return alarmFormView;
    }

    public EditAlarmView getEditAlarmView() {
        return editAlarmView;
    }

    public void setEditAlarmView(EditAlarmView editAlarmView) {
        this.editAlarmView = editAlarmView;
    }

    public AlarmingView getAlarmingView() {
        return alarmingView;
    }

    public void setAlarmingView(AlarmingView alarmingView) {
        this.alarmingView = alarmingView;
    }
}
