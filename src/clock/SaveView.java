package clock;

import javax.swing.*;
import java.awt.*;

public class SaveView extends JOptionPane {

    SaveModel model;
    JPanel panel;

    JLabel saveInfo;
    JLabel alarmInfo;

    public SaveView(SaveModel saveModel) {

        this.model = saveModel;
        panel = new JPanel();

        setPreferredSize(new Dimension(500, 100));

        saveInfo = new JLabel(model.saveMessage);
        saveInfo.setPreferredSize(new Dimension(400, 50));

        panel.add(saveInfo);
        //panel.add(alarmInfo);

        panel.setPreferredSize(new Dimension(450, 150));

        add(panel);
        showMessageDialog(null, panel);
        getRootFrame().pack();

    }
}
