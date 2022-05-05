package clock;

import java.awt.*;
import java.util.Observable;


public class EditAlarmModel extends Observable {

    final int PREF_HEIGHT = 400;
    final int PREF_WIDTH = 300;
    final Color PREF_BACKGROUND = Color.white;


    final Dimension spinnerArrows = new Dimension(30, 30);
    int daysRepeating = 0;
    Alarm alarm;

    public EditAlarmModel(Alarm alarm) {
        this.alarm = alarm;
    }

    public int getPREF_HEIGHT() {
        return PREF_HEIGHT;
    }

    public int getPREF_WIDTH() {
        return PREF_WIDTH;
    }

    public Color getPREF_BACKGROUND() {
        return PREF_BACKGROUND;
    }

    /*public Alarm getAlarm() {
        return this.alarm;
    }*/

    public Dimension getSpinnerArrows() {
        return spinnerArrows;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }
}