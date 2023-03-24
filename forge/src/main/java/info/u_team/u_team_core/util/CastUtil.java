package info.u_team.u_team_core.util;

/**
 * Utility methods for casts
 *
 * @author HyCraftHD
 */
public class CastUtil {
	
	/**
	 * Convenient helper method for unchecked class without suppressing warnings. CAREFULL, CAST IS STILL UNCHECKED!
	 *
	 * @param <T> The type that the object should be
	 * @param object The object
	 * @return Casted object to the desired type
	 * @throws ClassCastException when unchecked cast fails
	 */
	@SuppressWarnings("unchecked")
	public static <T> T uncheckedCast(Object object) throws ClassCastException {
		return (T) object;
	}
	
}
