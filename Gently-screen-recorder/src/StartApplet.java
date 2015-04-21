import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
//import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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






public class StartApplet extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	static Recorder recorder;
	JButton button;

	private JLabel headerLabel;
	private JPanel buttonPanel;
	private JPanel controlPanel;
	private JPanel radioPanel;
	
	JTextField streamKeyText;
	boolean withAudio = false;
	
	static AudioRec audioRec;
	static VideoAudioRec rec;
	
	 public static void main(String[] args) {
		 javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			 public void run() {
			
			     createAndShowGUI();
//			     audioRec = new AudioRec();
//			     audioRec.startRecording();
			     

			     
			 }
		 });


	 }

		
	 private static void createAndShowGUI() {
			
			  //Create and set up the window.
			  JFrame mainFrame = new StartApplet();

		
			  //Display the window.
			  mainFrame.pack();
			  mainFrame.setVisible(true);
			  mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	 }
		
	 public StartApplet() {
		  
		 			this.setSize(400, 400);
		 			this.setLayout(new GridLayout(4,1));

			        headerLabel = new JLabel("", JLabel.CENTER);  
		 			
			        controlPanel = new JPanel();
			        controlPanel.setLayout(new FlowLayout());
			        
			        radioPanel = new JPanel();
			        radioPanel.setLayout(new FlowLayout());
			        
			        buttonPanel = new JPanel();
			        buttonPanel.setLayout(new FlowLayout());
	
			        add(headerLabel);
			        add(controlPanel);
			        add(radioPanel);
			        add(buttonPanel);
			        
			        headerLabel.setText("GENTLY RECORDER");
			        
			        /////////

			        streamKeyText = new JTextField(6);          
			        JLabel  streamKeyLabel= new JLabel("Stream Key: ", JLabel.RIGHT);
			        
			        controlPanel.add(streamKeyLabel);
			        controlPanel.add(streamKeyText);
			        
			        /////////
			        
			        button = new JButton();
			        button.addActionListener(this);
			        button.setText("Start Recording");
			        button.setActionCommand("start");
			        button.setPreferredSize(new Dimension(150, 30));

			        buttonPanel.add(button);
			        
			        /////////
			        
			        final JRadioButton woA = new JRadioButton("Without Audio", true);
			        final JRadioButton wA = new JRadioButton("With Audio");
			        
			        woA.addItemListener(new ItemListener() {
			            public void itemStateChanged(ItemEvent e) {         
			               withAudio = false;
			            }           
			         });

			         wA.addItemListener(new ItemListener() {
			            public void itemStateChanged(ItemEvent e) {             
			               withAudio = true;
			            }           
			         });
			         
			         ButtonGroup group = new ButtonGroup();
			         group.add(woA);
			         group.add(wA);
			         
			         radioPanel.add(woA);
			         radioPanel.add(wA);
			         
	 }
	 
	 
	 
	 


	@Override
	public void actionPerformed(ActionEvent e) {
		
		String streamKey = streamKeyText.getText();
		
		
		if (e.getActionCommand().equals("start")) {
	    	
			if (withAudio) {
			    rec = new VideoAudioRec(streamKey);
			    rec.startRecording();
			     
			     System.out.println("START");
			    button.setActionCommand("stop");
			    button.setText("Stop Recording");
				
			} else {
				//start recording
		    	recorder = new Recorder(streamKey);
		    	recorder.startRecording();
		    	
		    	System.out.println("START");
		    	button.setActionCommand("stop");
		    	button.setText("Stop Recording");
			}
	    	
	      
	    } else if (e.getActionCommand().equals("stop")) {
	    	
	    	if (withAudio) {
	    		rec.stopRecording();
		    	rec = null;
		    	
		    	System.out.println("STOP");
		    	button.setText("Start Recording");
		    	button.setActionCommand("start");
			} else {
				recorder.stopRecording();
		    	recorder = null;
		    	
		    	System.out.println("STOP");
		    	button.setText("Start Recording");
		    	button.setActionCommand("start");
			}
	    	
	    }
		
	}
}


