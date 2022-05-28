package info.u_team.u_team_core.util;

import java.text.StringCharacterIterator;
import java.util.Locale;

public class SiUtil {
	
	public static String readableSi(long value) {
		if (-1000 < value && value < 1000) {
			return Long.toString(value) + " ";
		}
		final StringCharacterIterator ci = new StringCharacterIterator("kMGTPE");
		while (value <= -999_950 || value >= 999_950) {
			value /= 1000;
			ci.next();
		}
		
		return String.format(Locale.ROOT, "%.2f %c", value / 1000D, ci.current());
	}
	
}
