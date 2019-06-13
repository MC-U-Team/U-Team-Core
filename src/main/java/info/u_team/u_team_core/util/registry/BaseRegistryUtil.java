package info.u_team.u_team_core.util.registry;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.registry.IUArrayRegistryType;

public class BaseRegistryUtil {
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getClassEntriesFromArrayRegistryType(Class<T> classType, Class<?> init) {
		return Stream.of(init.getDeclaredFields()) //
				.parallel() //
				.filter(field -> field.getDeclaredAnnotation(Exclude.class) == null) //
				.filter(field -> IUArrayRegistryType.class.isAssignableFrom(field.getType())) //
				.map(field -> getStaticField(IUArrayRegistryType.class, field)) //
				.filter(arrayRegistryType -> arrayRegistryType.getArray().getClass().getComponentType().isAssignableFrom(classType)) //
				.collect(() -> new ArrayList<>(), (list, arrayRegistryType) -> list.addAll(Arrays.asList((T[]) arrayRegistryType.getArray())), (list, otherList) -> list.addAll(otherList));
	}
	
	public static <T> List<T> getClassEntries(Class<T> classType, Class<?> init) {
		return Stream.of(init.getDeclaredFields()) //
				.parallel() //
				.filter(field -> field.getDeclaredAnnotation(Exclude.class) == null) //
				.filter(field -> classType.isAssignableFrom(field.getType())) //
				.map(field -> getStaticField(classType, field)) //
				.collect(Collectors.toList());
	}
	
	private static <T> Stream<T> applyDefault(Class<T> castClass, Stream<Field> stream) {
		return stream.parallel() //
				.filter(field -> field.getDeclaredAnnotation(Exclude.class) == null) //
				.filter(field -> castClass.isAssignableFrom(field.getType())) //
				.map(field -> getStaticField(castClass, field));
	}
	
	private static <T> T getStaticField(Class<T> castClass, Field field) {
		try {
			return castClass.cast(field.get(null));
		} catch (Exception ex) {
			UCoreMain.logger.error("Failed fetching field entry {} for class {}.", field.getName(), field.getClass(), ex);
		}
		return null;
	}
	
	@Retention(value = RUNTIME)
	@Target(value = FIELD)
	public static @interface Exclude {
	}
	
}
