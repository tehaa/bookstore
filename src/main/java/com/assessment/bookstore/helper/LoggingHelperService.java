package com.assessment.bookstore.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class LoggingHelperService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingHelperService.class);

	public static void logInfo(String className, String methodName, String exitOrEntry) {

		LOGGER.info("{} {} {} ",exitOrEntry,className,methodName);
	}

}
