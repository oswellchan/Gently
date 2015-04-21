import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.xuggle.ferry.IBuffer;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IContainerFormat;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IAudioSamples.Format;
import com.xuggle.xuggler.ICodec.ID;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;
import com.xuggle.xuggler.IRational;


public class VideoAudioRec implements Runnable {
	private static String url = "rtmp://3283server2-i.comp.nus.edu.sg:1970/live1/";	  
	
//	IStreamCoder coderAudio;
//	IStreamCoder coderVideo;
	
	IMediaWriter writer;
	
    TargetDataLine line = null;
    AudioFormat audioFormat = null;
	
	IRational frameRate = IRational.make(25, 1);
	int videoStreamIndex = 0;
	int audioStreamIndex = 1;
	int sampleRateAudio = 48000; 
	
	
	public static boolean recording;
	
	
	public VideoAudioRec(String streamKey) {
		String temp = streamKey.concat(".flv");
		url = url.concat(temp);
		return;
	}

	@Override
	public void run() {
		
		int audioStreamIndex = 0;
		int streamAudioId = 0;
		int sampleRateAudio = 48000; 
		int channelAudio = 2;
		ID audioCodec = ICodec.ID.CODEC_ID_AAC;
		
		int videoStreamIndex = 1;
		int streamVideoId = 0;
		IRational frameRate = IRational.make(25, 1);
		ID videoCodec = ICodec.ID.CODEC_ID_H264;
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		writer = ToolFactory.makeWriter(url);
		
	    writer.addListener(new MediaListenerAdapter() {
	    	@Override
	    	public void onAddStream(IAddStreamEvent event) {
	    		configure(event);
	    	}
	    });
	    
		
		writer.addVideoStream(videoStreamIndex, streamVideoId, videoCodec, frameRate, width, height);
		writer.addAudioStream(audioStreamIndex, streamAudioId, audioCodec, channelAudio, sampleRateAudio);
		
		
        int sampleSize = 16;
        boolean bigEndian = false;		
		AudioFormat audioFormat = new AudioFormat(sampleRateAudio, sampleSize, channelAudio, true, bigEndian);
		
		TargetDataLine line = null;
	    DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat); 

	    if (!AudioSystem.isLineSupported(info)) {
	    	throw new RuntimeException("Line not supported"); 
	    }
	    // Obtain and open the line.
	    try {
	        line = (TargetDataLine) AudioSystem.getLine(info);
	        line.open(audioFormat);
	    } catch (LineUnavailableException ex) {
	    	throw new RuntimeException("Line Unavailable Exception"); 
	    }
	    
	    
	    
    
	    
	   
	    line.start();
	    
	    Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
        long now;
        BufferedImage image;
        Rectangle rec = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage currentScreenshot;
        IConverter converter;
        long timeStamp;
        long firstTimeStamp = System.currentTimeMillis();
        long startTime = System.nanoTime();
	    
	    while (recording) {
	    	// Audio /////////////////////////////////////////////////////////////////////////////
	    	
	        // read as raw bytes
	        byte[] audioBytes = new byte[ line.getBufferSize() / 2 ]; // best size?
	        int numBytesRead = 0;
	        numBytesRead =  line.read(audioBytes, 0, audioBytes.length);

	        // convert to signed shorts representing samples
	        int numSamplesRead = numBytesRead / 2;
	        short[] audioSamples = new short[ numSamplesRead ];
	        if (audioFormat.isBigEndian()) {
	            for (int i = 0; i < numSamplesRead; i++) {
	                audioSamples[i] = (short)((audioBytes[2*i] << 8) | audioBytes[2*i + 1]);
	            }
	        }
	        else {
	            for (int i = 0; i < numSamplesRead; i++) {
	                audioSamples[i] = (short)((audioBytes[2*i + 1] << 8) | audioBytes[2*i]);
	            }
	        }
		    // Audio /////////////////////////////////////////////////////////////////////////////
	        
	        
	        
	        
	        // Video /////////////////////////////////////////////////////////////////////////////
	        
	        now = System.currentTimeMillis();
            //grab the screenshot
            image = robot.createScreenCapture(rec);
            //convert it for Xuggler
            currentScreenshot = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            currentScreenshot.getGraphics().drawImage(image, 0, 0, null);
            //start the encoding process
            converter = ConverterFactory.createConverter(currentScreenshot, IPixelFormat.Type.YUV420P);
            timeStamp = (now - firstTimeStamp) * 1000; 
            IVideoPicture outFrame = converter.toPicture(currentScreenshot, timeStamp);
            
            // Video /////////////////////////////////////////////////////////////////////////////

            
            
            
            
	        // use audioSamples in Xuggler etc
	        writer.encodeAudio(audioStreamIndex, audioSamples);
	        writer.encodeVideo(videoStreamIndex, outFrame);
	    }
	    
		
	}
	
	
	
	
	
	public void configure(IAddStreamEvent event) 
    {
		
        int streamIndex = event.getStreamIndex();
        IStreamCoder streamCoder = event.getSource().getContainer().getStream(streamIndex).getStreamCoder();
        if(streamIndex == 0) {
        	streamCoder.setNumPicturesInGroupOfPictures(10);
        	//streamCoder.setCodec(ICodec.ID.CODEC_ID_H264);
        	streamCoder.setBitRate(450000);
        	streamCoder.setBitRateTolerance(10000);
        	streamCoder.setPixelType(IPixelFormat.Type.YUV420P);
        	streamCoder.setHeight(Toolkit.getDefaultToolkit().getScreenSize().height);
        	streamCoder.setWidth(Toolkit.getDefaultToolkit().getScreenSize().width);
        	streamCoder.setFlag(IStreamCoder.Flags.FLAG_QSCALE, true);
        	streamCoder.setGlobalQuality(0);
    	    
        	streamCoder.setFrameRate(IRational.make(25, 1));
        	streamCoder.setTimeBase(IRational.make(frameRate.getDenominator(), frameRate.getNumerator()));	
        	streamCoder.setProperty("coder", "1"); 
        	streamCoder.setProperty("flags", "+loop"); 
        	streamCoder.setProperty("cmp", "+chroma"); 
        	streamCoder.setProperty("partitions", "+parti8x8+parti4x4+partp8x8+partb8x8"); 
        	streamCoder.setProperty("me_method", "hex"); 
        	streamCoder.setProperty("subq", "6"); 
        	streamCoder.setProperty("me_range", "16"); 
            streamCoder.setProperty("g", "250"); 
            streamCoder.setProperty("keyint_min", "25"); 
            streamCoder.setProperty("sc_threshold", "40"); 
            streamCoder.setProperty("i_qfactor", "0.71"); 
            streamCoder.setProperty("b_strategy", "1"); 
            streamCoder.setProperty("qcomp", "0.6"); 
            streamCoder.setProperty("qmin", "10"); 
            streamCoder.setProperty("qmax", "51"); 
            streamCoder.setProperty("qdiff", "4"); 
            streamCoder.setProperty("directpred", "3"); 
            streamCoder.setProperty("bf", "16"); 
            streamCoder.setProperty("refs", "2"); 
            streamCoder.setProperty("directpred", "3");
            streamCoder.setProperty("trellis", "0");
            

            System.out.println("video");
        } else if(streamIndex == 1) {
    	    //ICodec cdc = ICodec.findEncodingCodec(ICodec.ID.CODEC_ID_AAC);
    	    //streamCoder.setCodec(cdc);
    	    streamCoder.setSampleRate(sampleRateAudio);
    	    streamCoder.setChannels(2);
    
    	    streamCoder.setFrameRate(IRational.make(sampleRateAudio,1));
    	    streamCoder.setSampleFormat(Format.FMT_S16);
    	    streamCoder.setBitRate(64000);
    	    

            System.out.println("audio");
        }
        //super.onAddStream(event);
    }
	
	
	
	public void startRecording() {
		recording = true;
		new Thread(this).start();
	}
	
	
	public void stopRecording() {
		recording = false;
		writer.close();
		
		try {
            Thread.sleep(100);
         } catch (Exception e) {
         }
	}
	
	
	
}
