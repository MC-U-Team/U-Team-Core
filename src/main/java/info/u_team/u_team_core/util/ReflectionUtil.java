package info.u_team.u_team_core.util;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class ReflectionUtil {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	public static Field findField(Class<?> clazz, String field) {
		return ObfuscationReflectionHelper.findField(clazz, field);
	}
	
	public static <I, V> void setValue(Field field, I instance, V value) {
		try {
			field.set(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException ex) {
			var exception = new AccessFieldException("", ex);
			LOGGER.error("Unable to set any field {} on type {}", field.getName(), field.getDeclaringClass(), exception);
			throw exception;
		}
	}
	
	public static <I, V> V getValue(Field field, I instance) {
		try {
			return CastUtil.uncheckedCast(field.get(instance));
		} catch (IllegalArgumentException | IllegalAccessException ex) {
			var exception = new AccessFieldException("", ex);
			LOGGER.error("Unable to access field {} on type {}", field.getName(), field.getDeclaringClass(), exception);
			throw exception;
		}
	}
	
	public static <O, N> void copyValue(Field field, O oldInstance, N newInstance) {
		setValue(field, newInstance, getValue(field, oldInstance));
	}
	
	private static class AccessFieldException extends RuntimeException {
		
		private static final long serialVersionUID = 1L;
		
		public AccessFieldException(String message, Throwable cause) {
			super(message, cause);
		}
	}
	
}
