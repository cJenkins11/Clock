/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.2
 Description: Alarm creation form model.
 */

package clock;

import java.awt.*;
import java.util.Observable;

public class AlarmFormModel extends Observable {

    final int PREF_HEIGHT = 250;
    final int PREF_WIDTH = 600;
    final Color PREF_BACKGROUND = Color.white;

    int daysRepeating = 0;
    Alarm alarm;

    public AlarmFormModel() {
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
     * @return - Prefered background colour as a Color object
     */
    public Color getPREF_BACKGROUND() {
        return PREF_BACKGROUND;
    }

    /**
     * @return - the new alarm that is being set
     */
    public Alarm getAlarm() {
        return alarm;
    }

    /**
     * Sets the new alarm
     * @param alarm - the new alarm that is being set
     */
    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }
}