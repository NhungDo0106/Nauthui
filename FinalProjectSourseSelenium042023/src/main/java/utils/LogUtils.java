package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {
        //Initialize Log4j instance
        private static final Logger LogUtils =  LogManager.getLogger(LogUtils.class);

        //Info Level Logs
        public static void info (String message) {
            LogUtils.info(message);
        }
        public static void info (Object object) {
            LogUtils.info(object);
        }

        //Warn Level Logs
        public static void warn (String message) {
            LogUtils.warn(message);
        }
        public static void warn (Object object) {
            LogUtils.warn(object);
        }

        //Error Level Logs
        public static void error (String message) {
            LogUtils.error(message);
        }
        public static void error (Object object) {
            LogUtils.error(object);
        }

        //Fatal Level Logs
        public static void fatal (String message) {
            LogUtils.fatal(message);
        }

        //Debug Level Logs
        public static void debug (String message) {
            LogUtils.debug(message);
        }
        public static void debug (Object object) {
            LogUtils.debug(object);
        }

}
