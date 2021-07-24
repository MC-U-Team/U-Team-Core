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

public class AnnotationManager {
	
	private static final Logger LOGGER = LogManager.getLogger("AnnotationManager");
	private static final Marker CONSTRUCT_MARKER = MarkerManager.getMarker("Construct");
	private static final Marker INTEGRATION_MARKER = MarkerManager.getMarker("Integration");
	
	public static void callAnnotations(String modid) {
		callConstructs(modid);
		callIntegrations(modid);
	}
	
	public static void callConstructs(String modid) {
		for (final AnnotationData data : AnnotationUtil.getAnnotations(modid, Type.getType(Construct.class))) {
			if (canBeCalled(modid, data)) {
				LOGGER.debug(CONSTRUCT_MARKER, "Load construct (" + data.memberName() + ") for mod " + modid);
				try {
					Class.forName(data.memberName()).asSubclass(IModConstruct.class).getConstructor().newInstance().construct();
				} catch (LinkageError | ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
					LOGGER.error(CONSTRUCT_MARKER, "Failed to load and call mod construct : {}", data.memberName(), ex);
					throw new RuntimeException(ex);
				}
			}
		}
	}
	
	public static void callIntegrations(String modid) {
		for (final AnnotationData data : AnnotationUtil.getAnnotations(modid, Type.getType(Integration.class))) {
			final String integrationModid = (String) data.annotationData().get("integration");
			if (canBeCalled(modid, data) && ModList.get().isLoaded(integrationModid)) {
				LOGGER.debug(INTEGRATION_MARKER, "Load " + integrationModid + " integration (" + data.memberName() + ") for mod " + modid);
				try {
					Class.forName(data.memberName()).asSubclass(IModIntegration.class).getConstructor().newInstance().construct();
				} catch (LinkageError | ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
					LOGGER.error(INTEGRATION_MARKER, "Failed to load and call integration : {}", data.memberName(), ex);
					throw new RuntimeException(ex);
				}
			}
		}
	}
	
	private static boolean canBeCalled(String modid, AnnotationData data) {
		final String annotationModid = (String) data.annotationData().get("modid");
		final Boolean client = (Boolean) data.annotationData().get("client");
		return modid.equals(annotationModid) && (client == null || !client || client && FMLEnvironment.dist == Dist.CLIENT);
	}
	
}
