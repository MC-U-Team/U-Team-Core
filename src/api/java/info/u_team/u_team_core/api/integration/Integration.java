package info.u_team.u_team_core.api.integration;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Integration {
	
	String modid();
	
	String value();
	
}
