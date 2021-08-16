package info.u_team.u_team_core.util;

public class CastUtil {

	@SuppressWarnings("unchecked")
	public static <T> T uncheckedCast(Object obj) {
		return (T) obj;
	}

}
