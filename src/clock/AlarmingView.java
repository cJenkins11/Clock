package clock;

import javax.sound.sampled.*;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

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

    public AlarmingView(AlarmingModel alarmingModel) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        model = alarmingModel;

        setPreferredSize(new Dimension(model.getPREF_WIDTH(), model.getPREF_HEIGHT()));
        setBackground(model.PREF_BACKGROUND);
        setTitle("Alarm! Alarm! Alarm!");

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        /*addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {
                model.getAlarmRinging().setSet(false);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                clip.stop();
                dispose();
            }
        });*/


        main = new JPanel();

        alarmName = new JLabel(model.getAlarmRinging().getAlarmName());
        alarmTime = new JLabel(model.getAlarmRinging().getFormattedSetTime());

        snooze = new JButton("Snooze");
        dismiss = new JButton("Dismiss");

        /*ActionListener dismissListener = e -> {
            clip.stop();
            //revalidate();
            dispose();
        };
        dismiss.addActionListener(dismissListener);*/


        main.add(alarmName);
        main.add(alarmTime);
        main.add(snooze);
        main.add(dismiss);

        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        clip.loop(Clip.LOOP_CONTINUOUSLY);

        clip.start();

        /*try {

            AudioClip clip = Applet.newAudioClip(new URL("file://C:/Users/callu/IdeaProjects/Clock/assets/wakeUp.wav"));
            clip.play();

        } catch (Exception e) {
            System.out.println(e);
        }*/


        /*Clip clip = AudioSystem.getClip();
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(AlarmingView.class.getResourceAsStream());

        clip.open(audioInput);
        clip.start();*/


        /*ActionListener createAlarm = e -> {
            AlarmFormView alarmFormView = new AlarmFormView(new AlarmFormModel());
        };
        newAlarm.addActionListener(createAlarm);*/

        //add();
        //add(checkAlarm);

        add(main);
        pack();
        setVisible(true);
    }

    public Clip getClip() {
        return clip;
    }

    public JButton getDismissButton() {
        return this.dismiss;
    }

    /*public JButton getNewAlarm() {
        return newAlarm;
    }*/

}
