package info.u_team.u_team_core.util;

import java.util.function.Predicate;

public class Predicates {

	public static <T> Predicate<T> not(Predicate<T> predicate) {
		return predicate.negate();
	}

}
