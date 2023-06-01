package info.u_team.u_team_core.util.annotation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.mojang.logging.LogUtils;

import info.u_team.u_team_core.api.Platform;
import info.u_team.u_team_core.api.Platform.Environment;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.api.integration.Integration;
import info.u_team.u_team_core.api.integration.ModIntegration;
import info.u_team.u_team_core.util.annotation.AnnotationUtil.AnnotationData;

/**
 * Manager for calling the classes that are annotated with {@link Construct} or {@link Integration}
 *
 * @author HyCraftHD
 */
public class AnnotationManager {
	
	private static final Logger LOGGER = LogUtils.getLogger();
	private static final Marker CONSTRUCT_MARKER = MarkerFactory.getMarker("Construct");
	private static final Marker INTEGRATION_MARKER = MarkerFactory.getMarker("Integration");
	
	/**
	 * Tries to invoke {@link ModConstruct#construct()} and {@link ModIntegration#construct()} methods of classes that are
	 * annotated with {@link Construct} or {@link Integration}.
	 *
	 * @param modid The modid that should be respected for calling
	 */
	public static void callAnnotations(String modid) {
		callConstructs(modid);
		callIntegrations(modid);
	}
	
	/**
	 * Tries to invoke all {@link ModConstruct#construct()} methods of classes that are annotated with {@link Construct}.
	 *
	 * @param modid The modid that should be respected for calling
	 */
	public static void callConstructs(String modid) {
		final List<AnnotationData> callable = new ArrayList<>();
		
		for (final AnnotationData data : AnnotationUtil.getAnnotations(modid, Type.getType(Construct.class))) {
			if (canBeCalled(modid, data)) {
				callable.add(data);
			}
		}
		
		callable.sort(sortByPriority());
		
		for (final AnnotationData data : callable) {
			LOGGER.debug(CONSTRUCT_MARKER, "Load construct (" + data.memberName() + ") for mod " + modid);
			try {
				Class.forName(data.memberName()).asSubclass(ModConstruct.class).getConstructor().newInstance().construct();
			} catch (LinkageError | ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
				LOGGER.error(CONSTRUCT_MARKER, "Failed to load and call mod construct : {}", data.memberName(), ex);
				throw new RuntimeException(ex);
			}
		}
	}
	
	/**
	 * Tries to invoke all {@link ModIntegration#construct()} methods of classes that are annotated with
	 * {@link Integration}.
	 *
	 * @param modid The modid that should be respected for calling
	 */
	public static void callIntegrations(String modid) {
		final List<AnnotationData> callable = new ArrayList<>();
		
		for (final AnnotationData data : AnnotationUtil.getAnnotations(modid, Type.getType(Integration.class))) {
			if (canBeCalled(modid, data) && Platform.getInstance().isModLoaded((String) data.annotationData().get("integration"))) {
				callable.add(data);
			}
		}
		
		callable.sort(sortByPriority());
		
		for (final AnnotationData data : callable) {
			LOGGER.debug(INTEGRATION_MARKER, "Load " + data.annotationData().get("integration") + " integration (" + data.memberName() + ") for mod " + modid);
			try {
				Class.forName(data.memberName()).asSubclass(ModIntegration.class).getConstructor().newInstance().construct();
			} catch (LinkageError | ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
				LOGGER.error(INTEGRATION_MARKER, "Failed to load and call integration : {}", data.memberName(), ex);
				throw new RuntimeException(ex);
			}
		}
	}
	
	/**
	 * Determines if an annotated class with the construct method can be called. Checks for the right modid and if the
	 * environment matches.
	 *
	 * @param modid The modid
	 * @param data Annotation data to check with
	 * @return True if method can be called
	 */
	private static boolean canBeCalled(String modid, AnnotationData data) {
		final String annotationModid = (String) data.annotationData().get("modid");
		final Boolean client = (Boolean) data.annotationData().get("client");
		return modid.equals(annotationModid) && (client == null || !client || client && Platform.getInstance().getEnvironment() == Environment.CLIENT);
	}
	
	private static Comparator<AnnotationData> sortByPriority() {
		final Function<AnnotationData, Pair<Boolean, Integer>> resolver = data -> {
			Boolean client = (Boolean) data.annotationData().get("client");
			if (client == null) {
				client = false;
			}
			Integer priority = (Integer) data.annotationData().get("priority");
			if (priority == null) {
				priority = 1000;
			}
			return Pair.of(client, priority);
		};
		
		return (first, second) -> {
			final Pair<Boolean, Integer> firstData = resolver.apply(first);
			final Pair<Boolean, Integer> secondData = resolver.apply(second);
			return firstData.compareTo(secondData);
		};
	}
	
}
