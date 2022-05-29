/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.3
 Description: Model for the view showing the user the active alarm
 */
package clock;

import java.awt.*;
import java.util.Observable;


public class AlarmingModel extends Observable {

    final int PREF_HEIGHT = 300;
    final int PREF_WIDTH = 400;
    final Color PREF_BACKGROUND = Color.WHITE;
    Alarm alarmRinging;

    /**
     * @param o - the alarm that is due to activate
     */
    public AlarmingModel(Alarm o) {
        alarmRinging = o;
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
     * @return - Alarm that is currently active
     */
    public Alarm getAlarmRinging() {
        return alarmRinging;
    }
}
