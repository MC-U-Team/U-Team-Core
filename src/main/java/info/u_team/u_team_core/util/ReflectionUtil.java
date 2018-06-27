package info.u_team.u_team_core.util;

import java.lang.reflect.*;

public class ReflectionUtil {
	
	public static void setFinal(Field field, Object instance, Object value) throws Exception {
		field.setAccessible(true);
		
		Field modifiers = Field.class.getDeclaredField("modifiers");
		modifiers.setAccessible(true);
		modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		
		field.set(instance, value);
	}
	
}
