import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import com.xuggle.ferry.IBuffer;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.IAudioSamples.Format;
import com.xuggle.xuggler.ICodec;
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


public class AudioRec implements Runnable {
	private static String url = "rtmp://3283server2-i.comp.nus.edu.sg:1970/live1/123";	  
	
	public static boolean recording;
	private static IContainer outContainer;
	private static IContainerFormat outContainerFormat;
	private static IStreamCoder outAudioCoder;
	private static IStream outAudioStream; 
	
	public AudioRec() {
		return;
	}

	@Override
	public void run() {
		
		
		
		
		
//		Mixer mixer = AudioSystem.getMixer(null); // default mixer
//		try {
//			mixer.open();
//		} catch (LineUnavailableException e) {
//			e.printStackTrace();
//		}
//
//		System.out.printf("Supported SourceDataLines of default mixer (%s):\n\n", mixer.getMixerInfo().getName());
//		for(Line.Info info : mixer.getSourceLineInfo()) {
//		    if(SourceDataLine.class.isAssignableFrom(info.getLineClass())) {
//		        SourceDataLine.Info info2 = (SourceDataLine.Info) info;
//		        System.out.println(info2);
//		        System.out.printf("  max buffer size: \t%d\n", info2.getMaxBufferSize());
//		        System.out.printf("  min buffer size: \t%d\n", info2.getMinBufferSize());
//		        AudioFormat[] formats = info2.getFormats();
//		        System.out.println("  Supported Audio formats: ");
//		        for(AudioFormat format : formats) {
//		            System.out.println("    "+format);
//		        }
//		    }
//		}
		
		
		int rateInt = 48000; 
		
		
		
		outContainer = IContainer.make();
	    outContainerFormat = IContainerFormat.make();
	    outContainerFormat.setOutputFormat("flv", url, null);
	    int retVal = outContainer.open(url, IContainer.Type.WRITE, outContainerFormat);
	    if (retVal < 0) {
	        System.out.println("Could not open output container");
	        return;
	    }
	    
	    outAudioStream = outContainer.addNewStream(0);


	    outAudioCoder = outAudioStream.getStreamCoder();
	    ICodec cdc = ICodec.findEncodingCodec(ICodec.ID.CODEC_ID_AAC);
	    outAudioCoder.setCodec(cdc);
	    outAudioCoder.setSampleRate(rateInt);
	    outAudioCoder.setChannels(2);

	    outAudioCoder.setFrameRate(IRational.make(rateInt,1));
	    outAudioCoder.setSampleFormat(Format.FMT_S16);
	    outAudioCoder.setBitRate(64000);

	    outAudioCoder.open(null,null);
	    if (retVal < 0) {
	    	throw new RuntimeException("Could not open audio coderCould not write output FLV header"); 
//	       System.out.println("Could not open audio coder");
//	        return;
	    }

	    retVal = outContainer.writeHeader();
	    if (retVal < 0) {
	    	throw new RuntimeException("Could not write output FLV header"); 
	        //System.out.println("Could not write output FLV header: ");
	        //return ;
	    }
	    
	    //AudioFormat audioFormat = new AudioFormat(44100.0f, (int)8, 0, true, false);
	    AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
	    float rate = 48000.0f;
        int channels = 2;
        int sampleSize = 16;
        boolean bigEndian = false;
	    //AudioFormat audioFormat = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian) ;
	    AudioFormat audioFormat = new AudioFormat(rate, sampleSize, channels, true, bigEndian);
        
	    TargetDataLine line = null;

	    DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat); 

	    if (!AudioSystem.isLineSupported(info)) {
	    	throw new RuntimeException("Line not supported"); 
//	    	System.out.println("Line not supported");
//	        return;
	    }

	    // Obtain and open the line.
	    try {
	        line = (TargetDataLine) AudioSystem.getLine(info);
	        line.open(audioFormat);
	    } catch (LineUnavailableException ex) {
	    	throw new RuntimeException("Line Unavailable Exception"); 
//	    	System.out.println("Line Unavailable Exception");
//	    	return;
	    }



	    // Begin audio capture.
	    line.start();

	    long lastPos_out=0;
	    long audtioTime =0;
	    long startTime = System.nanoTime();
	    
	    while(true)
	    {
	        //byte[] data = new byte[line.getBufferSize()/2];
	    	byte[] data = new byte[line.getBufferSize()/5];
	        int sz = line.read(data, 0, data.length);

	        
	        if(sz>0) {
	        	long nanoTs = System.nanoTime()-startTime; 
	            IBuffer iBuf = IBuffer.make(null,data,0,sz); 
	            System.out.println(iBuf.toString());
		        IAudioSamples pSamples = IAudioSamples.make(iBuf, 2,IAudioSamples.Format.FMT_S16);
		        pSamples.setComplete(true,sz/2,rateInt,2,Format.FMT_S16,  nanoTs / 1000);
		        
		        int samplesConsumed = 0;
		        //System.out.println("pSamples = " + pSamples.getNumSamples());
		        while(samplesConsumed<pSamples.getNumSamples()){
		        	IPacket packet= IPacket.make(); 
			        //outAudioCoder.encodeAudio(packet, as, 0);	 
		        	samplesConsumed += outAudioCoder.encodeAudio(packet, pSamples, samplesConsumed);
		        	
		        	//System.out.println("samplesConsumed = " + samplesConsumed);
		        	
		        	
			        if (packet.isComplete()) {
			        	packet.setPosition(lastPos_out);
		                packet.setStreamIndex(0);
		                lastPos_out += packet.getSize();          
			        	outContainer.writePacket(packet);
			        }
		        }
		        
		        
	        }
//	        int numSamplesRead = sz / 2;
//	        short[] audioSamples = new short[ numSamplesRead ];
//	        
//	        if (audioFormat.isBigEndian()) {
//	            for (int i = 0; i < numSamplesRead; i++) {
//	                audioSamples[i] = (short)((data[2*i] << 8) | data[2*i + 1]);
//	            }
//	        }
//	        else {
//	            for (int i = 0; i < numSamplesRead; i++) {
//	                audioSamples[i] = (short)((data[2*i + 1] << 8) | data[2*i]);
//	            }
//	        }
	        
	        
	        
	        
	               
	        
	        
	        
	        

	    }

	}





	public void startRecording() {
		recording = true;
		new Thread(this).start();
	}
}
