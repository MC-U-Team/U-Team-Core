package info.u_team.u_team_core.util.annotation;

import org.apache.logging.log4j.*;
import org.objectweb.asm.Type;

import info.u_team.u_team_core.api.construct.*;
import info.u_team.u_team_core.api.integration.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;

public class AnnotationManager {
	
	private static final Logger LOGGER = LogManager.getLogger("AnnotationManager");
	private static final Marker CONSTRUCT_MARKER = MarkerManager.getMarker("Construct");
	private static final Marker INTEGRATION_MARKER = MarkerManager.getMarker("Integration");
	
	public static void callConstructs(String modid) {
		for (final AnnotationData data : AnnotationUtil.getAnnotations(modid, Type.getType(Construct.class))) {
			if (canBeCalled(modid, data)) {
				LOGGER.info(CONSTRUCT_MARKER, "Load construct (" + data.getMemberName() + ") for mod " + modid);
				try {
					Class.forName(data.getMemberName()).asSubclass(IModConstruct.class).newInstance().construct();
				} catch (LinkageError | ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException ex) {
					LOGGER.error(CONSTRUCT_MARKER, "Failed to load and call mod construct : {}", data.getMemberName(), ex);
					throw new RuntimeException(ex);
				}
			}
		}
	}
	
	public static void callIntegrations(String modid) {
		for (final AnnotationData data : AnnotationUtil.getAnnotations(modid, Type.getType(Integration.class))) {
			final String integrationModid = (String) data.getAnnotationData().get("integration");
			if (canBeCalled(modid, data) && ModList.get().isLoaded(integrationModid)) {
				LOGGER.info(INTEGRATION_MARKER, "Load " + integrationModid + " integration (" + data.getMemberName() + ") for mod " + modid);
				try {
					Class.forName(data.getMemberName()).asSubclass(IModIntegration.class).newInstance().construct();
				} catch (LinkageError | ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException ex) {
					LOGGER.error(INTEGRATION_MARKER, "Failed to load and call integration : {}", data.getMemberName(), ex);
					throw new RuntimeException(ex);
				}
			}
		}
	}
	
	private static boolean canBeCalled(String modid, AnnotationData data) {
		final String annotationModid = (String) data.getAnnotationData().get("modid");
		final Boolean client = (Boolean) data.getAnnotationData().get("client");
		return modid.equals(annotationModid) && (client == null || !client || client && FMLEnvironment.dist == Dist.CLIENT);
	}
	
}
