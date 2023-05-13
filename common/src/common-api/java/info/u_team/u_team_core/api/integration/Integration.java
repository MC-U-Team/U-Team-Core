package info.u_team.u_team_core.api.integration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Integration {
	
	String modid();
	
	boolean client() default false;
	
	String integration();
	
}
