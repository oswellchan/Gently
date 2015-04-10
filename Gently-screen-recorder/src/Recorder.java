//import com.xuggle.xuggler.Configuration;
import com.xuggle.ferry.IBuffer;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.IAudioSamples.Format;
import com.xuggle.xuggler.ICodec;
//import com.xuggle.xuggler.ICodec.ID;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IContainerFormat;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IRational;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;

public class Recorder implements Runnable{

	private static String url = "rtmp://3283server2-i.comp.nus.edu.sg:1970/live1/123";	  
	
	public static boolean recording;
	public IContainer container;
	
	public Recorder() {
		return;
	}

	@Override
	public void run() {
	       container = IContainer.make();
	       IContainerFormat containerFormat_live = IContainerFormat.make();
	       containerFormat_live.setOutputFormat("flv", url, null);
	       container.setInputBufferLength(0);
	       
	       int retVal = container.open(url, IContainer.Type.WRITE, containerFormat_live);
	   
	       if (retVal < 0) {
	           System.err.println("Could not open output container for live stream");
	           System.exit(1);
	       }
	       
	       
	       IStream stream = container.addNewStream(ICodec.ID.CODEC_ID_H264);
	       
	       IStreamCoder coder = stream.getStreamCoder();
	       
	       //VIDEO
	       coder.setNumPicturesInGroupOfPictures(10);
	       coder.setCodec(ICodec.ID.CODEC_ID_H264);
	       coder.setBitRate(450000);
	       coder.setBitRateTolerance(10000);
	       coder.setPixelType(IPixelFormat.Type.YUV420P);
	       coder.setHeight(Toolkit.getDefaultToolkit().getScreenSize().height);
	       coder.setWidth(Toolkit.getDefaultToolkit().getScreenSize().width);
	       coder.setFlag(IStreamCoder.Flags.FLAG_QSCALE, true);
	       coder.setGlobalQuality(0);
	       IRational frameRate = IRational.make(25, 1);
	       coder.setFrameRate(IRational.make(25, 1));
	       coder.setTimeBase(IRational.make(frameRate.getDenominator(), frameRate.getNumerator()));	
	       coder.setProperty("coder", "1"); 
           coder.setProperty("flags", "+loop"); 
           coder.setProperty("cmp", "+chroma"); 
           coder.setProperty("partitions", "+parti8x8+parti4x4+partp8x8+partb8x8"); 
           coder.setProperty("me_method", "hex"); 
           coder.setProperty("subq", "6"); 
           coder.setProperty("me_range", "16"); 
           coder.setProperty("g", "250"); 
           coder.setProperty("keyint_min", "25"); 
           coder.setProperty("sc_threshold", "40"); 
           coder.setProperty("i_qfactor", "0.71"); 
           coder.setProperty("b_strategy", "1"); 
           coder.setProperty("qcomp", "0.6"); 
           coder.setProperty("qmin", "10"); 
           coder.setProperty("qmax", "51"); 
           coder.setProperty("qdiff", "4"); 
           coder.setProperty("directpred", "3"); 
           coder.setProperty("bf", "16"); 
           coder.setProperty("refs", "2"); 
           coder.setProperty("directpred", "3");
           coder.setProperty("trellis", "0"); 
           //coder.setProperty("flags2", "+bpyramid+wpred+dct8x8+fastpskip"); 
           //coder.setProperty("cqp", "0"); 
           
          

	       
//	       Properties props = new Properties();
//	       System.out.println(new File(getClass().getResource("/").getFile()).getAbsolutePath());
//	       InputStream is = Recorder.class.getResourceAsStream("/libx264-normal.ffpreset");
//	       try {
//	           props.load(is);
//	       } catch (IOException e) {
//	           System.err.println("You need the libx264-normal.ffpreset file from the Xuggle distribution in your classpath.");
//	           System.exit(1);
//	       }
//	       Configuration.configure(props, coder);
           
           //VIDEO
	       coder.open(null, null);
	       container.writeHeader();
	       long firstTimeStamp = System.currentTimeMillis();
	       int i = 0;
	       
	       try {
	    	   //VIDEO
	           Robot robot = new Robot();
	           long now;
	           BufferedImage image;
	           //Rectangle rec = new Rectangle(x, y, width, height);
	           Rectangle rec = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	           BufferedImage currentScreenshot;
	           IPacket packet;
	           IConverter converter;
	           long timeStamp;
	           
	           
	          
	           
	           //while (i < framesToEncode) {
	           while (recording) {
	        	   
	        	  
	        	   
	            
	            	   //VIDEO
	            	   long iterationStartTime = System.currentTimeMillis();
		               now = System.currentTimeMillis();
		               //grab the screenshot
		               image = robot.createScreenCapture(rec);
		               //convert it for Xuggler
		               currentScreenshot = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		               currentScreenshot.getGraphics().drawImage(image, 0, 0, null);
		               //start the encoding process
		               packet = IPacket.make();
		               converter = ConverterFactory.createConverter(currentScreenshot, IPixelFormat.Type.YUV420P);
		               timeStamp = (now - firstTimeStamp) * 1000; 
		               IVideoPicture outFrame = converter.toPicture(currentScreenshot, timeStamp);
		               if (i == 0) {
		                   //make first frame keyframe
		                   outFrame.setKeyFrame(true);
		               }
		               outFrame.setQuality(0);
		               coder.encodeVideo(packet, outFrame, 0);
		               outFrame.delete();
		               
		              
		               
		              

	            	  
		               if (packet.isComplete()) {  
		            
		                   container.writePacket(packet);
		                   
		                   i++;
			               try {
			                   // sleep for framerate milliseconds
			                   Thread.sleep(Math.max((long) (1000 / frameRate.getDouble()) - (System.currentTimeMillis() - now), 0));
			               } catch (InterruptedException e) {
			                   e.printStackTrace();
			               }
		               }
	               
	        	   
	        	   
	        	   
	        	   
	        	   
	               
	               
	           }
	       } catch (AWTException e) {
	           e.printStackTrace();
	       }
	       
	       container.writeTrailer();
	}




	public void stopRecording() {
		recording = false;
		container.close();
		
		try {
            Thread.sleep(100);
         } catch (Exception e) {
         }
		
	}

	public void startRecording() {
		recording = true;
		new Thread(this).start();
	}
		
		
	
}
