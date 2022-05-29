/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.1
 Description: Model for the help message popup
 */

package clock;

public class HelpModel {

    String title;

    String message;

    String functionMenuMessage;

    String alarmsMessage;

    /**
     * Initialises the help messages with a default message in HTML
     */
    public HelpModel() {
        title = "Help";

        alarmsMessage = "<html>The list of alarms created will show on the right of the app" +
                "<br>" +
                "They can be edited, deleted, or toggled on/off" +
                "<br>" +
                "If an alarm is updated, it's position in the queue does not</html>";

        functionMenuMessage = "<html>The functions of the alarm are on the left." +
                "<br>" +
                "You can save a file of your set alarms using the 'New Alarm' button " +
                "<br>" +
                "You can load an alarm file using the 'Load File' button.</html>";
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

}
