package info.u_team.u_team_core.util;

import java.util.*;

/**
 * Creates a non null list custom with some basic parameters
 * 
 * @author HyCraftHD
 * @date 05.07.2018
 */

public class NonNullListUtil {
	
	public static <E> NonNullListCustom<E> create() {
		return new NonNullListCustom<>(new ArrayList<>());
	}
	
	@SafeVarargs
	public static <E> NonNullListCustom<E> from(E defaultelement, E... elements) {
		return new NonNullListCustom<>(Arrays.asList(elements), defaultelement);
	}
	
}
