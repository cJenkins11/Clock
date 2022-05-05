package clock;

import java.awt.*;
import java.util.Observable;

public class FunctionMenuModel extends Observable {

    final int PREF_HEIGHT = 400;
    final int PREF_WIDTH = 200;
    final Color PREF_BACKGROUND = Color.white;



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
