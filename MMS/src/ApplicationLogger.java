import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ApplicationLogger {
	static private final String _fileName = "MMSLog.txt";
	static private FileHandler _fileHandler = null;
	static private SimpleFormatter _txtFormatter = null;

	static public void setup() throws IOException {

		// get the global logger to configure it
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

		logger.setLevel(Level.INFO);
		_fileHandler = new FileHandler(_fileName);

		// create a TXT formatter
		_txtFormatter = new SimpleFormatter();
		_fileHandler.setFormatter(_txtFormatter);
		
		logger.addHandler(_fileHandler);
	}
}
