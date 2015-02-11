import java.applet.Applet;

import javax.swing.SwingUtilities;


public class StartApplet extends Applet {

	public void init() {
		//Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                	@SuppressWarnings("unused")
					JRecorder jRecorder = new JRecorder();
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
        }
	}
}
