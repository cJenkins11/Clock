/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.1
 Description: View for the load alarm pop up
 */

package clock;

import net.fortuna.ical4j.data.ParserException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoadView extends JOptionPane {

    LoadModel model;
    JPanel panel;
    JLabel loadInfo;

    /**
     * Brings up a pop up informing the user that any set alarms will be overwritten by the ones being loaded
     * @param loadModel - Model of the load alarm pop up
     * @throws IOException
     * @throws ParserException
     */
    public LoadView(LoadModel loadModel) throws IOException, ParserException {
        model = loadModel;

        panel = new JPanel();

        setPreferredSize(new Dimension(model.getPREF_WIDTH(), model.getPREF_HEIGHT()));

        loadInfo = new JLabel(model.getLoadMessage());
        loadInfo.setPreferredSize(new Dimension(300, 50));

        panel.add(loadInfo);

        panel.setPreferredSize(new Dimension(model.getPREF_WIDTH(), model.getPREF_HEIGHT()));

        ImageIcon icon = new ImageIcon("assets/folder.png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);

        add(panel);

        int input = JOptionPane.showConfirmDialog(null,
                loadInfo, "Select an Option...",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon);

        // 0=yes, 1=no, 2=cancel
        if (input == 0) {

                model.load();

        }

        getRootFrame().pack();
    }
}
