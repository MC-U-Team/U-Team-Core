package info.u_team.u_team_core.util;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

/**
 * Utility methods for reflection stuff
 *
 * @author HyCraftHD
 */
public class ReflectionUtil {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	/**
	 * Method to find a field in a give class. The name will be mapped to the right mappings. Will return the field with
	 * widened access.
	 *
	 * @param clazz Class of the field
	 * @param field The name of the field
	 * @return The field
	 */
	public static Field findField(Class<?> clazz, String field) {
		return ObfuscationReflectionHelper.findField(clazz, field);
	}
	
	/**
	 * Sets the value of a field. Catches exceptions and throws a runtime exception.
	 *
	 * @param field The field
	 * @param instance The object instance. Null for static fields
	 * @param value The value
	 */
	public static <I, V> void setValue(Field field, I instance, V value) {
		try {
			field.set(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException ex) {
			final AccessFieldException exception = new AccessFieldException("", ex);
			LOGGER.error("Unable to set any field {} on type {}", field.getName(), field.getDeclaringClass(), exception);
			throw exception;
		}
	}
	
	/**
	 * Gets the value of a field. Catches exceptions and throws a runtime exception.
	 *
	 * @param field The field
	 * @param instance The object instance. Null for static fields
	 * @return The value
	 */
	public static <I, V> V getValue(Field field, I instance) {
		try {
			return CastUtil.uncheckedCast(field.get(instance));
		} catch (IllegalArgumentException | IllegalAccessException | ClassCastException ex) {
			final AccessFieldException exception = new AccessFieldException("", ex);
			LOGGER.error("Unable to access field {} on type {}", field.getName(), field.getDeclaringClass(), exception);
			throw exception;
		}
	}
	
	/**
	 * Utility method to copy a field value from an old oject instance to a new instance. Catches exceptions and throws a
	 * runtime exception.
	 *
	 * @param field The field
	 * @param oldInstance Old object
	 * @param newInstance New object
	 */
	public static <O, N> void copyValue(Field field, O oldInstance, N newInstance) {
		setValue(field, newInstance, getValue(field, oldInstance));
	}
	
	/**
	 * Runtime exception for field access exceptions.
	 *
	 * @author HyCraftHD
	 */
	private static class AccessFieldException extends RuntimeException {
		
		private static final long serialVersionUID = 1L;
		
		public AccessFieldException(String message, Throwable cause) {
			super(message, cause);
		}
	}
	
}
