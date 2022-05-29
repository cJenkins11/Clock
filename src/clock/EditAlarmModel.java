/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.1
 Description: Model of the view used to edit an alarm
 */

package clock;

import java.awt.*;
import java.util.Observable;


public class EditAlarmModel extends Observable {

    final int PREF_HEIGHT = 250;
    final int PREF_WIDTH = 600;
    final Color PREF_BACKGROUND = Color.white;

    int daysRepeating = 0;
    Alarm alarm;

    /**
     * Constructor for model
     * @param alarm - Alarm to be edited
     */
    public EditAlarmModel(Alarm alarm) {
        this.alarm = alarm;
    }

    /**
     * @return - int of preferred height
     */
    public int getPREF_HEIGHT() {
        return PREF_HEIGHT;
    }

    /**
     * @return - int of preferred width
     */
    public int getPREF_WIDTH() {
        return PREF_WIDTH;
    }

    /**
     * @return - Color of preferred background colour
     */
    public Color getPREF_BACKGROUND() {
        return PREF_BACKGROUND;
    }

    /**
     * @return - Alarm being edited
     */
    public Alarm getAlarm() {
        return alarm;
    }

    /**
     * @param alarm - alarm set to be edited
     */
    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }
}