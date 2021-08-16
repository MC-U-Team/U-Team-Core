package info.u_team.u_team_core.util;

@FunctionalInterface
public interface TriConsumer<A, B, C> {
	
	void accept(A a, B b, C c);
}
