package info.u_team.u_team_core.api.construct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Construct {
	
	String modid();
	
	boolean client() default false;
	
	int priority() default 1000;
	
}
