/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.2
 Description: Model for the load alarm view. Responsible for reading the mycalander.ics file and creating alarms from its data
 */

package clock;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;


public class LoadModel {

    public String loadMessage;
    private final String loadFile = "mycalendar.ics";
    final int PREF_HEIGHT = 200;
    final int PREF_WIDTH = 500;
    final Color PREF_BACKGROUND = Color.white;

    /**
     * Constructor
     * Initialises the load message
     */
    public LoadModel() {
        loadMessage = "<html>Loading alarms will overwrite any currently set.<br> Proceed?</html>";
    }

    /**
     * Load alarm function. Parses mycalander.ics and creates alarms from its data
     * @throws IOException
     * @throws ParserException
     */
    public void load() throws IOException, ParserException {

        System.setProperty("net.fortuna.ical4j.timezone.cache.impl", "net.fortuna.ical4j.util.MapTimeZoneCache");

        FileInputStream input = new FileInputStream(loadFile);

        CalendarBuilder builder = new CalendarBuilder();
        //Issues with build, not implemented
        Calendar calendar = builder.build(input);

        /*for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            System.out.println("Component [" + component.getName() + "]");

            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
                Property property = (Property) j.next();
                System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
            }
        }*/
    }

    /**
     * @return - String of the load message
     */
    public String getLoadMessage() {
        return loadMessage;
    }

    /**
     * @return - int of preferred width
     */
    public int getPREF_WIDTH() {
        return PREF_WIDTH;
    }

    /**
     * @return - int of preferred height
     */
    public int getPREF_HEIGHT() {
        return PREF_HEIGHT;
    }

    /**
     * @return Color of preferred background colour
     */
    public Color getPREF_BACKGROUND() {
        return PREF_BACKGROUND;
    }
}
