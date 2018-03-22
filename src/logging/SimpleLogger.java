package logging;

public class SimpleLogger {
	
	public enum LogLevel {
		COMP,
		DEBUG,
		ALL
	}
	
	public enum LogSubsystem {
		AUTO,
		ALL
	}
	
	private static LogLevel enabledLogLevel = LogLevel.ALL;
	private static LogSubsystem enabledLogSubsystem = LogSubsystem.ALL;
	private static final boolean NO_REPEAT_MESSAGES = false;
	
	private static String previousMessage = null;
	
	public static void log(String message, LogLevel level, LogSubsystem subsystem){
		if(enabledLogLevel == LogLevel.ALL || level == enabledLogLevel){
			if(enabledLogSubsystem == LogSubsystem.ALL || subsystem == enabledLogSubsystem){
				printMessage(message);
			}
		}
	}
	
	public static void log(String message){
		LogLevel level = LogLevel.DEBUG;
		LogSubsystem subsystem = LogSubsystem.ALL;
		
		if(enabledLogLevel == LogLevel.ALL || level == enabledLogLevel){
			if(enabledLogSubsystem == LogSubsystem.ALL || subsystem == enabledLogSubsystem){
				printMessage(message);
			}
		}
	}
	
	private static void printMessage(String message){
		if(NO_REPEAT_MESSAGES){
			if(!message.equals(previousMessage)){
				System.out.println(message);
				previousMessage = message;
			}
		}else{
			System.out.println(message);
			previousMessage = message;
		}
	}
}
