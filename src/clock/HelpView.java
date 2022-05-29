/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.1
 Description: View of the help pop up. Displays information about the Alarm Clock program
 */

package clock;

import javax.swing.*;
import java.awt.*;

public class HelpView extends JOptionPane {

    HelpModel model;
    JPanel panel;

    JLabel functionInfo;
    JLabel alarmInfo;

    /**
     * Initiates the view components
     * @param helpModel - Model of the help pop up
     */
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
