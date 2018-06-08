package info.u_team.u_team_core.repack.org.slf4j.event;

import static info.u_team.u_team_core.repack.org.slf4j.event.EventConstants.*;

/**
 * 
 * @author ceki
 * @since 1.7.15
 */
public enum Level {
	
	ERROR(ERROR_INT, "ERROR"),
	WARN(WARN_INT, "WARN"),
	INFO(INFO_INT, "INFO"),
	DEBUG(DEBUG_INT, "DEBUG"),
	TRACE(TRACE_INT, "TRACE");
	
	private int levelInt;
	private String levelStr;
	
	Level(int i, String s) {
		levelInt = i;
		levelStr = s;
	}
	
	public int toInt() {
		return levelInt;
	}
	
	/**
	 * Returns the string representation of this Level.
	 */
	public String toString() {
		return levelStr;
	}
	
}
