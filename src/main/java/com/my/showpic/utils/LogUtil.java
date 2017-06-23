package com.my.showpic.utils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 * LogUtil, used to record logs.
 * 
 */
public class LogUtil {

	// Root logger.
	private static final Logger rootLogger = Logger.getRootLogger();

	// Log file root file
	private static String logFileRootFolder = "";

	// DB log file name
	private static final String DATA_BASE_LOG_FILENAME = "database.log";

	// Common log file name
	private static final String NORMAL_LOG_FILENAME = "server.log";

	// Log level
	private static Level logLevel = Level.WARN;

	static {
		initLogParams();
	}

	/**
	 * Get DB logger.
	 * 
	 * @param clazz
	 *            Class object
	 * @return DBlogger
	 */
	@SuppressWarnings("rawtypes")
	public static Logger getDBLogger(Class clazz) {
		return getLogger(clazz, DATA_BASE_LOG_FILENAME);
	}

	/**
	 * Get server logger.
	 * 
	 * @param clazz
	 *            Class object
	 * @return Server logger
	 */
	@SuppressWarnings("rawtypes")
	public static Logger getServerLogger(Class clazz) {
		return getLogger(clazz, NORMAL_LOG_FILENAME);
	}

	/*
	 * Initialize log parameters.
	 */
	private static void initLogParams() {
		RollingFileAppender appender = (RollingFileAppender) rootLogger.getAppender("showpic");

		// Initialize log file root path
		if (null != appender) {
			String logFilePath = appender.getFile();
			logFileRootFolder = logFilePath.substring(0, logFilePath.lastIndexOf("/")) + File.separator;
		} else {
			// This is used for unit test scenario.
			logFileRootFolder = "target/logs/";
		}

		// Initialize log level
		logLevel = rootLogger.getLevel();
	}

	/*
	 * Get logger.
	 * 
	 * @param clazz Class object
	 * 
	 * @param logFileName log file name
	 * 
	 * @return Logger
	 */
	@SuppressWarnings("rawtypes")
	private static Logger getLogger(Class clazz, String logFileName) {
		return getLogger(clazz, logFileName, logLevel);
	}

	/*
	 * Get logger.
	 * 
	 * @param clazz Class object
	 * 
	 * @param logFileName log file name
	 * 
	 * @param level Log level
	 * 
	 * @return Logger
	 */
	@SuppressWarnings("rawtypes")
	private static Logger getLogger(Class clazz, String logFileName, Level level) {
		Logger logger = Logger.getLogger(clazz);
		logger.removeAllAppenders();
		logger.setAdditivity(false);
		logger.setLevel(level);
		PatternLayout layout = new PatternLayout();
		layout.setConversionPattern("%-d{yyyy-MM-dd HH:mm:ss SSS} [%c]-[%p] - %m%n");
		try {
			RollingFileAppender appender = new RollingFileAppender(layout, logFileRootFolder + logFileName);
			appender.setMaxFileSize("20MB");
			appender.setMaxBackupIndex(10);
			logger.addAppender(appender);
		} catch (IOException e) {
			rootLogger.error("IOException occurred while getting logger.", e);
		}
		return logger;
	}
}
