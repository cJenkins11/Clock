package clock;

import java.awt.*;
import java.util.Observable;

public class AlarmingModel extends Observable {

    final int PREF_HEIGHT = 300;
    final int PREF_WIDTH = 400;
    final Color PREF_BACKGROUND = Color.WHITE;



    public int getPREF_HEIGHT() {
        return PREF_HEIGHT;
    }

    public int getPREF_WIDTH() {
        return PREF_WIDTH;
    }

    public Color getPREF_BACKGROUND() {
        return PREF_BACKGROUND;
    }
}
