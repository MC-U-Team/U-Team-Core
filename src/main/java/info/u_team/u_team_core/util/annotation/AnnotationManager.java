package info.u_team.u_team_core.util.annotation;

import java.lang.reflect.InvocationTargetException;

import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.mojang.logging.LogUtils;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.api.integration.Integration;
import info.u_team.u_team_core.api.integration.ModIntegration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;

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
		for (final AnnotationData data : AnnotationUtil.getAnnotations(modid, Type.getType(Construct.class))) {
			if (canBeCalled(modid, data)) {
				LOGGER.debug(CONSTRUCT_MARKER, "Load construct (" + data.memberName() + ") for mod " + modid);
				try {
					Class.forName(data.memberName()).asSubclass(ModConstruct.class).getConstructor().newInstance().construct();
				} catch (LinkageError | ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
					LOGGER.error(CONSTRUCT_MARKER, "Failed to load and call mod construct : {}", data.memberName(), ex);
					throw new RuntimeException(ex);
				}
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
		for (final AnnotationData data : AnnotationUtil.getAnnotations(modid, Type.getType(Integration.class))) {
			final String integrationModid = (String) data.annotationData().get("integration");
			if (canBeCalled(modid, data) && ModList.get().isLoaded(integrationModid)) {
				LOGGER.debug(INTEGRATION_MARKER, "Load " + integrationModid + " integration (" + data.memberName() + ") for mod " + modid);
				try {
					Class.forName(data.memberName()).asSubclass(ModIntegration.class).getConstructor().newInstance().construct();
				} catch (LinkageError | ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
					LOGGER.error(INTEGRATION_MARKER, "Failed to load and call integration : {}", data.memberName(), ex);
					throw new RuntimeException(ex);
				}
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
		return modid.equals(annotationModid) && (client == null || !client || client && FMLEnvironment.dist == Dist.CLIENT);
	}
	
}
