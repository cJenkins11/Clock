/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.2
 Description: View of the save alarms pop up
 */

package clock;

import javax.swing.*;
import java.awt.*;

public class SaveView extends JOptionPane {

    SaveModel model;
    JPanel panel;
    JLabel saveInfo;

    /**
     * Pop up informing users that their alarms have been saved
     * @param saveModel - Model of the save alarms pop up
     */
    public SaveView(SaveModel saveModel) {

        this.model = saveModel;
        panel = new JPanel();

        setPreferredSize(new Dimension(500, 100));

        saveInfo = new JLabel(model.saveMessage);
        saveInfo.setPreferredSize(new Dimension(400, 50));

        panel.add(saveInfo);

        panel.setPreferredSize(new Dimension(450, 150));

        add(panel);
        showMessageDialog(null, panel);
        getRootFrame().pack();
    }
}
