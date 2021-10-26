package info.u_team.u_team_core.util;

/**
 * Consumer with three parameters
 *
 * @author HyCraftHD
 */
@FunctionalInterface
public interface TriConsumer<A, B, C> {
	
	void accept(A a, B b, C c);
}
