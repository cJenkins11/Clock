package clock;

public class Test {

    public static void main(String[] args) throws QueueUnderflowException {
        AlarmQueueModel queue = new AlarmQueueModel<>();
        //new PriorityItem<>(new Node(, null), 1);
        Alarm alarm = new Alarm("0100", "AM", "1", 31);
        Alarm alarm2 = new Alarm("0200", "AM", "2", 31);
        Alarm alarm3 = new Alarm("0400", "AM", "2", 31);
        Alarm alarm4 = new Alarm("0700", "AM", "2", 31);

        queue.add(alarm, 1);
        queue.add(alarm2, 2);
        queue.add(alarm3, 4);
        queue.add(alarm4, 7);

        //Node testRemoveNode = new Node<>(alarm);
        //System.out.println(testRemoveNode);
        System.out.println(alarm4);
        //queue.remove(alarm);
        //queue.remove(alarm2);
        queue.remove(alarm2);
        //queue.remove(testRemoveNode);
        System.out.println(queue);
    }
}
