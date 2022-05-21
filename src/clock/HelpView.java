package clock;

import javax.swing.*;
import java.awt.*;

public class HelpView extends JOptionPane {

    HelpModel model;
    JPanel panel;

    JLabel functionInfo;
    JLabel alarmInfo;


    public HelpView(HelpModel helpModel) {
        this.model = helpModel;
        panel = new JPanel();

        setPreferredSize(new Dimension(500, 100));

        functionInfo = new JLabel(model.functionMenuMessage);
        functionInfo.setPreferredSize(new Dimension(400, 50));


        alarmInfo = new JLabel(model.alarmsMessage);
        alarmInfo.setPreferredSize(new Dimension(400, 50));

        panel.add(functionInfo);
        panel.add(alarmInfo);

        panel.setPreferredSize(new Dimension(450, 150));

        add(panel);
        showMessageDialog(null, panel);
        getRootFrame().pack();
    }
}
