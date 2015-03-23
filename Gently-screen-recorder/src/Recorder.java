import com.xuggle.xuggler.Configuration;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.ICodec.ID;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Recorder implements Runnable{

	private static String url = "rtmp://mediatech-i.comp.nus.edu.sg:1935/live1/123";	  
	
	public static boolean recording;
	public IContainer container;
	
	public Recorder() {
		return;
	}

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
	       
	       
	       IStream stream = container.addNewStream(ID.CODEC_ID_H264);
	       IStreamCoder coder = stream.getStreamCoder();
	       coder.setNumPicturesInGroupOfPictures(5);
	       coder.setCodec(ICodec.ID.CODEC_ID_H264);
	       coder.setBitRate(450000);
	       coder.setBitRateTolerance(5000);
	       coder.setPixelType(IPixelFormat.Type.YUV420P);
	       coder.setHeight(Toolkit.getDefaultToolkit().getScreenSize().height);
	       coder.setWidth(Toolkit.getDefaultToolkit().getScreenSize().width);
	       coder.setFlag(IStreamCoder.Flags.FLAG_QSCALE, true);
	       coder.setGlobalQuality(0);
	       IRational frameRate = IRational.make(25, 1);
	       coder.setFrameRate(IRational.make(25, 1));
	       coder.setTimeBase(IRational.make(frameRate.getDenominator(), frameRate.getNumerator()));			
	       
	       Properties props = new Properties();
	       InputStream is = Recorder.class.getResourceAsStream("/libx264-normal.ffpreset");
	       try {
	           props.load(is);
	       } catch (IOException e) {
	           System.err.println("You need the libx264-normal.ffpreset file from the Xuggle distribution in your classpath.");
	           System.exit(1);
	       }
	       Configuration.configure(props, coder);
	       coder.open(null, null);
	       container.writeHeader();
	       long firstTimeStamp = System.currentTimeMillis();
	       int i = 0;
	       
	       try {
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
	               //long iterationStartTime = System.currentTimeMillis();
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
	               }
	               i++;
	               try {
	                   // sleep for framerate milliseconds
	                   Thread.sleep(Math.max((long) (1000 / frameRate.getDouble()) - (System.currentTimeMillis() - now), 0));
	               } catch (InterruptedException e) {
	                   e.printStackTrace();
	               }
	           }
	       } catch (AWTException e) {
	           e.printStackTrace();
	       }
	       
	       container.writeTrailer();
	}




	public void stopRecording() {
		recording = false;
		//container.close();
		
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
