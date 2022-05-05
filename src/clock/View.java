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

    public View(Model model) {
        frame = new JFrame();
        panel = new ClockPanel(model);

        //functionMenuView = new FunctionMenuView(model.getFunctionMenuModel());
        //alarmQueueView = new AlarmQueueView(model.getAlarmQueueModel());


        /*
        functionMenuModel = new FunctionMenuModel();
        functionMenuView = new FunctionMenuView(functionMenuModel);
        functionMenuModel.addObserver(functionMenuView);
        FunctionController functionController = new FunctionController(functionMenuModel, functionMenuView);
*/

        //frame.setContentPane(panel);
        frame.setTitle("Java Clock");
        frame.setPreferredSize(new Dimension(800, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Start of border layout code
        
        // I've just put a single button in each of the border positions:
        // PAGE_START (i.e. top), PAGE_END (bottom), LINE_START (left) and
        // LINE_END (right). You can omit any of these, or replace the button
        // with something else like a label or a menu bar. Or maybe you can
        // figure out how to pack more than one thing into one of those
        // positions. This is the very simplest border layout possible, just
        // to help you get started.
        
        Container pane = frame.getContentPane();
        
        /*JButton button = new JButton("Button 1 (PAGE_START)");
        pane.add(button, BorderLayout.PAGE_START);*/
         
        panel.setPreferredSize(new Dimension(400, 400));
        pane.add(panel, BorderLayout.CENTER);
        //pane.add(functionMenuView, BorderLayout.LINE_START);

        //pane.add(alarmQueueView, BorderLayout.LINE_END);



        /*button = new JButton("Button 3 (LINE_START)");
        pane.add(button, BorderLayout.LINE_START);*/
         
        /*button = new JButton("Long-Named Button 4 (PAGE_END)");
        pane.add(button, BorderLayout.PAGE_END);*/
         
        /*button = new JButton("5 (LINE_END)");
        pane.add(button, BorderLayout.LINE_END);*/
        
        // End of borderlayout code
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public void update(Observable o, Object arg) {
        panel.repaint();
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
}
