import java.awt.BorderLayout;
//import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.jnlp.*;


//public class StartApplet extends JApplet implements ActionListener {
//	
//	private static final long serialVersionUID = 1L;
//	static JButton control;
//	static Recorder recorder;
//
//	
//	
//    //Called when this applet is loaded into the browser.
//	@Override
//    public void init() {
//        //Execute a job on the event-dispatching thread; creating this applet's GUI.
//    	//JRecorder jRecorder = new JRecorder();
//    	control = new JButton("Start Recording");
//    	control.setActionCommand("start");
//    	control.addActionListener(this);
//    	add(control);
//
//    }
//    
//	@Override
//    public void start() {
//		recorder = new Recorder();
//    	recorder.startRecording();
//    }
//	
//	@Override
//	public void stop() {
//		recorder.stopRecording();
//    	recorder = null;
//	}
//
//	public void actionPerformed(ActionEvent e) {
//		if (e.getActionCommand().equals("start")) {
//	    	
//	    	//start recording
//	    	recorder = new Recorder();
//	    	recorder.startRecording();
//	    	
//	    	//System.out.println("START");
//	    	StartApplet.control.setActionCommand("stop");
//	    	StartApplet.control.setText("Stop Recording");
//	      
//	    } else if (e.getActionCommand().equals("stop")) {
//	    	
//	    	recorder.stopRecording();
//	    	recorder = null;
//	    	
//	    	//System.out.println("STOP");
//	    	StartApplet.control.setText("Start Recording");
//	    	StartApplet.control.setActionCommand("start");
//	    }
//		
//	}
//}






public class StartApplet extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Recorder recorder;

	 public static void main(String[] args) {
		 javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			 public void run() {
			
			     createAndShowGUI();
			     recorder = new Recorder();
			     recorder.startRecording();
			     
			 
			 }
		 });


	 }

		
	 private static void createAndShowGUI() {
			
			  //Create and set up the window.
			  JFrame frame = new StartApplet();

		
			  //Display the window.
			  frame.pack();
			  frame.setVisible(true);
			  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	 }
		
	 public StartApplet() {
		
			        // set flow layout for the frame
		
			        this.getContentPane().setLayout(new FlowLayout());
			
			        JButton button1 = new JButton();
		
			        button1.setText("Start Recording");
	
		
			        // add buttons to frame
	
			        add(button1);
			
	 }
}


