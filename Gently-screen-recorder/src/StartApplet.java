import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class StartApplet extends JApplet implements ActionListener {
	
	static JButton control;
	static Recorder recorder;

	
	
    //Called when this applet is loaded into the browser.
    public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
    	//JRecorder jRecorder = new JRecorder();
    	control = new JButton("Start Recording");
    	control.setActionCommand("start");
    	control.addActionListener(this);
    	add(control);

    }

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("start")) {
	    	
	    	//start recording
	    	recorder = new Recorder();
	    	recorder.startRecording();
	    	
	    	System.out.println("START");
	    	StartApplet.control.setActionCommand("stop");
	    	StartApplet.control.setText("Stop Recording");
	      
	    } else if (e.getActionCommand().equals("stop")) {
	    	
	    	recorder.stopRecording();
	    	recorder = null;
	    	
	    	System.out.println("STOP");
	    	StartApplet.control.setText("Start Recording");
	    	StartApplet.control.setActionCommand("start");
	    }
		
	}
}
