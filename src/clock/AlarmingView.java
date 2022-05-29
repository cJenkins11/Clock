/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.1
 Description: View for the currently active alarm. Displays name and time, plays an alarm noise, and has snooze and dismiss buttons
 */

package clock;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AlarmingView extends JFrame {

    AlarmingModel model;
    JPanel main;

    JLabel alarmName;
    JLabel alarmTime;

    JButton snooze;
    JButton dismiss;

    AudioInputStream audioInputStream;
    Clip clip;
    static String filePath = "assets/wakeUp.wav";


    /**
     * View to display the current alarm to the user
     * @param alarmingModel - Model for the view, contains the current alarm
     * @throws LineUnavailableException
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    public AlarmingView(AlarmingModel alarmingModel) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        model = alarmingModel;

        setPreferredSize(new Dimension(model.getPREF_WIDTH(), model.getPREF_HEIGHT()));
        setBackground(model.PREF_BACKGROUND);
        setTitle("Alarm! Alarm! Alarm!");

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        main = new JPanel();
        main.setLayout(new GridLayout(3, 1));

        JPanel alarmNamePanel = new JPanel();
        alarmNamePanel.setLayout(new FlowLayout());

        JPanel alarmTimePanel = new JPanel();
        alarmTimePanel.setLayout(new FlowLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        JLabel alarmNameLabel = new JLabel("Alarm Name: ");
        alarmName = new JLabel(model.getAlarmRinging().getAlarmName());

        JLabel alarmTimeLabel = new JLabel("Time: ");
        alarmTime = new JLabel(model.getAlarmRinging().getFormattedSetTime());

        dismiss = new JButton("Dismiss");

        alarmNamePanel.add(alarmNameLabel);
        alarmNamePanel.add(alarmName);

        alarmTimePanel.add(alarmTimeLabel);
        alarmTimePanel.add(alarmTime);

        buttonsPanel.add(dismiss);

        main.add(alarmNamePanel);
        main.add(alarmTimePanel);
        main.add(buttonsPanel);

        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        clip.loop(Clip.LOOP_CONTINUOUSLY);

        clip.start();

        add(main);

        pack();
        setVisible(true);
    }

    /**
     * @return - Audio clip used as alarm sound
     */
    public Clip getClip() {
        return clip;
    }

    /**
     * @return - JButton for dismissing alarm
     */
    public JButton getDismissButton() {
        return this.dismiss;
    }



}
