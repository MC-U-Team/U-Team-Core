package info.u_team.u_team_core.util.annotation;

import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.objectweb.asm.Type;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.IModConstruct;
import info.u_team.u_team_core.api.integration.IModIntegration;
import info.u_team.u_team_core.api.integration.Integration;
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
	
	private static final Logger LOGGER = LogManager.getLogger("AnnotationManager");
	private static final Marker CONSTRUCT_MARKER = MarkerManager.getMarker("Construct");
	private static final Marker INTEGRATION_MARKER = MarkerManager.getMarker("Integration");
	
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
				LOGGER.debug(CONSTRUCT_MARKER, "Load construct (" + data.getMemberName() + ") for mod " + modid);
				try {
					Class.forName(data.getMemberName()).asSubclass(IModConstruct.class).getConstructor().newInstance().construct();
				} catch (LinkageError | ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
					LOGGER.error(CONSTRUCT_MARKER, "Failed to load and call mod construct : {}", data.getMemberName(), ex);
					throw new RuntimeException(ex);
				}
			}
		}
	}
	
	/**
	 * Tries to invoke all {@link IModIntegrationg#construct()} methods of classes that are annotated with
	 * {@link Integration}.
	 *
	 * @param modid The modid that should be respected for calling
	 */
	public static void callIntegrations(String modid) {
		for (final AnnotationData data : AnnotationUtil.getAnnotations(modid, Type.getType(Integration.class))) {
			final String integrationModid = (String) data.getAnnotationData().get("integration");
			if (canBeCalled(modid, data) && ModList.get().isLoaded(integrationModid)) {
				LOGGER.debug(INTEGRATION_MARKER, "Load " + integrationModid + " integration (" + data.getMemberName() + ") for mod " + modid);
				try {
					Class.forName(data.getMemberName()).asSubclass(IModIntegration.class).getConstructor().newInstance().construct();
				} catch (LinkageError | ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
					LOGGER.error(INTEGRATION_MARKER, "Failed to load and call integration : {}", data.getMemberName(), ex);
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
		final String annotationModid = (String) data.getAnnotationData().get("modid");
		final Boolean client = (Boolean) data.getAnnotationData().get("client");
		return modid.equals(annotationModid) && (client == null || !client || client && FMLEnvironment.dist == Dist.CLIENT);
	}
	
}