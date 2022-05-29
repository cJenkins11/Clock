/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.2
 Description: Model for the save alarm pop up
 */

package clock;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveModel {

    public String saveMessage;
    Node head;

    /**
     * Model responsible for saving the set alarms to mycalendar.ics
     * @param head - The start of the list of alarms
     */
    public SaveModel(Object head) {

        this.head = (Node) head;
        this.saveMessage = "Alarms Saved";
    }

    /**
     * Builds a Calendar object and for each alarm set, it creates a VEvent to add to the calendar.
     * @throws IOException
     */
    public void save() throws IOException {

        String calFile = "mycalendar.ics";
        UidGenerator uidGenerator;

        //Calendar
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Jenkins Software Corporation//Alarm Clock//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);


        //For each alarm in the queue, create an event
        int UIDs = 1;
        for (Node node = head; node != null; node = node.getNext()) {

            java.util.Calendar test = java.util.Calendar.getInstance();

            PriorityItem item = (PriorityItem) node.getItem();

            test.set(test.YEAR, test.MONTH, test.DATE, ((Alarm)item.getItem()).getHour(), ((Alarm)item.getItem()).getMinute());

            VEvent testEvent = new VEvent(new Date(test.getTime()), ((Alarm) item.getItem()).getAlarmName());

            int finalUIDs = UIDs;
            uidGenerator = () -> new Uid(Integer.toString(finalUIDs));
            testEvent.getProperties().add(uidGenerator.generateUid());

            calendar.getComponents().add(testEvent);
            UIDs++;
        }

        //Saving an iCalendar file
        FileOutputStream fout = new FileOutputStream(calFile);

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.setValidating(false);
        outputter.output(calendar, fout);

    }
}
