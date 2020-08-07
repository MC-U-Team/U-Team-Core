package info.u_team.u_team_core.api.construct;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Construct {
	
	boolean client() default false;
	
}
