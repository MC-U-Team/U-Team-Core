package info.u_team.u_team_core.integration;

import org.apache.logging.log4j.*;
import org.objectweb.asm.Type;

import info.u_team.u_team_core.api.integration.*;
import info.u_team.u_team_core.util.AnnotationUtil;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;

public class IntegrationManager {
	
	private static final Logger LOGGER = LogManager.getLogger("IntegrationManager");
	
	public static void constructIntegrations(String modid) {
		for (AnnotationData data : AnnotationUtil.getAnnotations(modid, Type.getType(Integration.class))) {
			final String annotationModid = (String) data.getAnnotationData().get("modid");
			final String integrationModid = (String) data.getAnnotationData().get("integration");
			if (modid.equals(annotationModid)) {
				if (ModList.get().isLoaded(integrationModid)) {
					LOGGER.info("Try to load " + integrationModid + " integration for mod " + modid);
					try {
						Class.forName(data.getMemberName()).asSubclass(IModIntegration.class).newInstance().construct();
					} catch (LinkageError | ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException ex) {
						LOGGER.error("Failed to load and construct integration : {}", data.getMemberName(), ex);
						throw new RuntimeException(ex);
					}
				}
			}
		}
	}
}
