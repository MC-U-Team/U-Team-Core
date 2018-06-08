package info.u_team.u_team_core.repack.org.slf4j.event;

import info.u_team.u_team_core.repack.org.slf4j.Marker;

/**
 * 
 * @author ceki
 * @since 1.7.15
 */
public interface LoggingEvent {
	
	Level getLevel();
	
	Marker getMarker();
	
	String getLoggerName();
	
	String getMessage();
	
	String getThreadName();
	
	Object[] getArgumentArray();
	
	long getTimeStamp();
	
	Throwable getThrowable();
	
}
