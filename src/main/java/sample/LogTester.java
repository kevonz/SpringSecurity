package sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.impl.StaticLoggerBinder;

public class LogTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Logger logger = LoggerFactory.getLogger(LogTester.class);	    
	    logger.trace("Hello World");
	    logger.debug("Hello World");
	    logger.info("Hello World");
	    logger.warn("Hello World");
	    logger.error("Hello World");
	}

}
