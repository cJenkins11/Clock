package clock;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class SaveModel {




    public String saveMessage;
    Node head = null;


    public SaveModel(Object head) {

        this.head = (Node) head;
        this.saveMessage = "Alarms Saved";

    }

    public void save() throws IOException, ParserException {

        String calFile = "mycalendar.ics";

        //Calendar
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Jenkins Software Corporation//Alarm Clock//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        //Event
        java.util.Calendar eventCal = java.util.Calendar.getInstance();
        eventCal.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
        eventCal.set(java.util.Calendar.DAY_OF_MONTH, 25);

        VEvent christmas = new VEvent(new Date(eventCal.getTime()), "Christmas Day");
        // initialise as an all-day event..

        //christmas.getProperties().getProperty(Property.DTSTART).getParameters().add(Value.DATE);

        UidGenerator uidGenerator = new UidGenerator() {
            @Override
            public Uid generateUid() {
                return null;
            }
        };
        christmas.getProperties().add(uidGenerator.generateUid());

        calendar.getComponents().add(christmas);

        //Saving an iCalendar file
        FileOutputStream fout = new FileOutputStream(calFile);

        CalendarOutputter outputter = new CalendarOutputter();
        outputter.setValidating(false);
        outputter.output(calendar, fout);

        //Now Parsing an iCalendar file
        FileInputStream fin = new FileInputStream(calFile);

        CalendarBuilder builder = new CalendarBuilder();

        calendar = builder.build(fin);

        //Iterating over a Calendar
        for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            System.out.println("Component [" + component.getName() + "]");

            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
                Property property = (Property) j.next();
                System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
            }
        }//for
    }


        /*for (Node node = head; node != null; node = node.getNext()) {

            //PriorityItem item = (PriorityItem) node.getItem();



        }*/

}
