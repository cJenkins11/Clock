/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.4
 Description: JUnit test for the main funcitons of the alarm clock.
                    - Create new alarm
                    - Save alarms
                    - Help message pop up
                    - Edit alarm
                    - Toggle alarm
                    - Delete alarm
                    - Alarm notification
 */

package clock;

import org.junit.Test;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import static org.junit.Assert.*;

public class ModelTest {

    Controller controller;
    Model model;
    View view;

    public ModelTest() throws QueueUnderflowException {

        model = new Model();
        view = new View(model);
        model.addObserver(view);
        Controller controller = new Controller(model, view);
        view.frame.setVisible(false);

        //System.out.println(model.getAlarmQueueModel().head());
        assertTrue(model.getAlarmQueueModel().isEmpty());
    }

    /**
     * Creates a test alarm and add it to the alarm queue model
     * Assert that the head of the queue is equal to the test alarm
     *
     * @throws QueueUnderflowException
     */
    @Test
    public void createAlarmTest() throws QueueUnderflowException {

        System.out.println("-CREATE ALARM TEST-");

        String testTime = "1130";
        String testAM_PM = "AM";
        String testName = "Test alarm 1";
        int setDays = 0;
        int priority;

        Alarm testAlarm = new Alarm(testTime, testAM_PM, testName, setDays);
        System.out.println("Test Alarm:\n" + testAlarm + "\n");

        if (model.currentTimeMins() > testAlarm.getSetTimeMins()) {
            priority = (testAlarm.getSetTimeMins() + 1440) - model.currentTimeMins();
        } else {
            priority = testAlarm.getSetTimeMins() - model.currentTimeMins();
        }

        model.getAlarmQueueModel().add(testAlarm, priority);
        System.out.println("Model head:\n" + model.getAlarmQueueModel().head() + "\n");

        PriorityItem modelHead = (PriorityItem) model.getAlarmQueueModel().head();
        Alarm modelAlarm = (Alarm) modelHead.getItem();

        System.out.println("Test set time: " + testAlarm.setTime +
                "\nModel head set time: " + modelAlarm.getSetTime());

        assertEquals(testAlarm.getSetTime(), modelAlarm.getSetTime());
        assertEquals(testAlarm.getAM_PM(), modelAlarm.getAM_PM());
        assertEquals(testAlarm.getAlarmName(), modelAlarm.getAlarmName());
        assertEquals(testAlarm.getSetDays(), modelAlarm.getSetDays());
        assertTrue(testAlarm.isSet());
        System.out.println("-END OF CREATE ALARM TEST-");

    }


    /**
     * Creates a new alarm
     * Saves the test alarm created to "mycalendar.ics"
     * Ensures the correct information is inputted by printing the contents of mycalendar.ics
     */
    @Test
    public void saveAlarmTest() throws QueueUnderflowException, IOException {
        System.out.println("-SAVE ALARM TEST-");

        String testTime = "1200";
        String testAM_PM = "PM";
        String testName = "Alarm save test";
        int setDays = 0;
        int priority;

        Alarm testAlarm = new Alarm(testTime, testAM_PM, testName, setDays);
        System.out.println("Test Alarm:\n" + testAlarm + "\n");

        if (model.currentTimeMins() > testAlarm.getSetTimeMins()) {
            priority = (testAlarm.getSetTimeMins() + 1440) - model.currentTimeMins();
        } else {
            priority = testAlarm.getSetTimeMins() - model.currentTimeMins();
        }
        model.getAlarmQueueModel().add(testAlarm, priority);

        //Simulate user clicking save and initiating a new save model
        model.setSaveModel(new SaveModel(model.getAlarmQueueModel().headNode()));

        model.getSaveModel().save();

        InputStream input = new BufferedInputStream(new FileInputStream("mycalendar.ics"));
        byte[] buffer = new byte[8192];

        try {
            for (int length = 0; (length = input.read(buffer)) != -1;) {
                System.out.write(buffer, 0, length);
            }
        } finally {
            input.close();
        }

        testTime = "0930";
        testAM_PM = "PM";
        testName = "Alarm save test 2";

        Alarm testAlarm2 = new Alarm(testTime, testAM_PM, testName, setDays);
        System.out.println("\nTest Alarm 2:\n" + testAlarm2 + "\n");

        if (model.currentTimeMins() > testAlarm.getSetTimeMins()) {
            priority = (testAlarm.getSetTimeMins() + 1440) - model.currentTimeMins();
        } else {
            priority = testAlarm.getSetTimeMins() - model.currentTimeMins();
        }

        model.getAlarmQueueModel().add(testAlarm2, priority);
        model.getSaveModel().save();

        input = new BufferedInputStream(new FileInputStream("mycalendar.ics"));
        buffer = new byte[8192];

        try {
            for (int length = 0; (length = input.read(buffer)) != -1;) {
                System.out.write(buffer, 0, length);
            }
        } finally {
            input.close();
        }

        System.out.println("-END OF SAVE ALARM TEST-");
    }


    /**
     * Tests the help message pop up
     */
    @Test
    public void helpTest() {
        System.out.println("-HELP TEST-");

        //Simulate user clicking help and initiating a new help model
        model.setHelpModel(new HelpModel());

        assertNotNull(model.getHelpModel());

        view.setHelpView(new HelpView(model.getHelpModel()));

        System.out.println("-END OF HELP TEST-");
    }


    /**
     * Creates and adds an alarm to the queue and tests the getters/setters and priority update functions
     * @throws QueueUnderflowException
     */
    @Test
    public void editTest() throws QueueUnderflowException {
        System.out.println("-EDIT ALARM TEST-");

        controller = new Controller(model, view);

        String testTime = "1200";
        String testAM_PM = "PM";
        String testName = "Alarm edit test";
        int setDays = 0;
        int priority;

        Alarm testAlarm = new Alarm(testTime, testAM_PM, testName, setDays);
        System.out.println("Test Alarm:\n" + testAlarm + "\n");

        if (model.currentTimeMins() > testAlarm.getSetTimeMins()) {
            priority = (testAlarm.getSetTimeMins() + 1440) - model.currentTimeMins();
        } else {
            priority = testAlarm.getSetTimeMins() - model.currentTimeMins();
        }
        model.getAlarmQueueModel().add(testAlarm, priority);

        //Simulate user clicking edit button
        PriorityItem item = (PriorityItem) model.getAlarmQueueModel().headNode().getItem();
        model.setEditAlarmModel(new EditAlarmModel((Alarm) item.getItem()));

        //Changes to be made
        String testEditTime = "0100";
        String testEditAM_PM = "AM";
        String testEditName = "The name has been changed";

        //Simulate edit
        model.getEditAlarmModel().getAlarm().setSetTime(testEditTime);
        model.getEditAlarmModel().getAlarm().setAM_PM(testEditAM_PM);
        model.getEditAlarmModel().getAlarm().setAlarmName(testEditName);

        if (testEditAM_PM == "PM") {
            item.setPriority(Integer.parseInt(Integer.toString(model.getEditAlarmModel().getAlarm().getHour()) + Integer.toString(model.getEditAlarmModel().getAlarm().getMinute())) + 1200);

        } else {
            item.setPriority(Integer.parseInt(Integer.toString(model.getEditAlarmModel().getAlarm().getHour()) + Integer.toString(model.getEditAlarmModel().getAlarm().getMinute())) + 1200);
        }

        controller.updatePriorities();

        System.out.println("Test Alarm after editing:\n" + model.getAlarmQueueModel().head() + "\n");

        //Check the test alarm was the same alarm that was edited
        assertEquals(testAlarm, ((PriorityItem)model.getAlarmQueueModel().headNode().getItem()).getItem());

        System.out.println("-END OF EDIT ALARM TEST-");
    }


    /**
     * Tests the toggle alarm and check set alarm functions
     * @throws QueueUnderflowException
     */
    @Test
    public void testToggle() throws QueueUnderflowException {
        System.out.println("-TOGGLE ALARM TEST-");

        String testTime = "1200";
        String testAM_PM = "PM";
        String testName = "Alarm toggle test";
        int setDays = 0;
        int priority;

        Alarm testAlarm = new Alarm(testTime, testAM_PM, testName, setDays);
        System.out.println("Test Alarm:\n" + testAlarm + "\n");

        if (model.currentTimeMins() > testAlarm.getSetTimeMins()) {
            priority = (testAlarm.getSetTimeMins() + 1440) - model.currentTimeMins();
        } else {
            priority = testAlarm.getSetTimeMins() - model.currentTimeMins();
        }
        model.getAlarmQueueModel().add(testAlarm, priority);

        System.out.println("Is set?: " + ((Alarm) ((PriorityItem)model.getAlarmQueueModel().head()).getItem()).isSet());
        System.out.println(model.checkSetAlarms());
        assertEquals(model.checkSetAlarms(), 1);

        //Simulate toggle
        PriorityItem item = (PriorityItem) model.getAlarmQueueModel().headNode().getItem();
        ((Alarm) item.getItem()).setSet(false);

        System.out.println("Is set?: " + ((Alarm) ((PriorityItem)model.getAlarmQueueModel().head()).getItem()).isSet());
        System.out.println(model.checkSetAlarms());
        assertEquals(model.checkSetAlarms(), 0);

        System.out.println("-END OF TOGGLE ALARM TEST-");
    }

    /**
     * Creates a new alarm and removes it from the queue
     * @throws QueueUnderflowException
     */
    @Test
    public void testDelete() throws QueueUnderflowException {
        System.out.println("-DELETE ALARM TEST-");

        String testTime = "0100";
        String testAM_PM = "PM";
        String testName = "Delete alarm test";
        int setDays = 0;
        int priority;

        Alarm testAlarm = new Alarm(testTime, testAM_PM, testName, setDays);
        System.out.println("Test Alarm:\n" + testAlarm + "\n");

        if (model.currentTimeMins() > testAlarm.getSetTimeMins()) {
            priority = (testAlarm.getSetTimeMins() + 1440) - model.currentTimeMins();
        } else {
            priority = testAlarm.getSetTimeMins() - model.currentTimeMins();
        }
        model.getAlarmQueueModel().add(testAlarm, priority);
        System.out.println("Queue before removal:\n" + model.getAlarmQueueModel() + "\n");

        model.getAlarmQueueModel().remove((Alarm)((PriorityItem)model.getAlarmQueueModel().head()).getItem());

        System.out.println("Queue after removal:\n" + model.getAlarmQueueModel());

        System.out.println("-END OF DELETE ALARM TEST-");
    }


    /**
     * Creates a new alarm instance set for the current time. Briefly initialises an alarm notification
     * @throws QueueUnderflowException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     * @throws IOException
     */
    @Test
    public void testNotification() throws QueueUnderflowException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        controller = new Controller(model, view);
        String testTime;
        String testAM_PM;


        Calendar date;
        date = Calendar.getInstance();

        System.out.println(date.getTime().getHours());

        int hours = date.getTime().getHours();
        if (hours > 12) {

            hours -= 12;

            if (hours < 10) {
                testTime = "0" + Integer.toString(hours);
            } else {
                testTime = Integer.toString(hours);
            }

            testAM_PM = "PM";

        } else {

            if (hours < 10) {
                testTime = "0" + Integer.toString(hours);
            } else {
                testTime = Integer.toString(hours);
            }

            testAM_PM = "AM";
        }


        int minutes = date.getTime().getMinutes();
        if (minutes < 10) {
            testTime += "0" + Integer.toString(minutes);
        } else {
            testTime += Integer.toString(minutes);
        }


        System.out.println(testTime);

        String testName = "Test alarm notification";
        int setDays = 0;
        int priority;

        Alarm testAlarm = new Alarm(testTime, testAM_PM, testName, setDays);
        System.out.println("Test Alarm:\n" + testAlarm + "\n");

        if (model.currentTimeMins() > testAlarm.getSetTimeMins()) {
            priority = (testAlarm.getSetTimeMins() + 1440) - model.currentTimeMins();
        } else {
            priority = testAlarm.getSetTimeMins() - model.currentTimeMins();
        }
        model.getAlarmQueueModel().add(testAlarm, priority);
        controller.initAlarmAlert(model.checkSetAlarms());
    }

}













