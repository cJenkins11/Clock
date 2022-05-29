/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.0
 Description: Initiates the main model, view, and controller
 */
package clock;

public class Clock {
    
    public static void main(String[] args) throws QueueUnderflowException {
        Model model = new Model();
        View view = new View(model);
        model.addObserver(view);
        Controller controller = new Controller(model, view);
    }
}
