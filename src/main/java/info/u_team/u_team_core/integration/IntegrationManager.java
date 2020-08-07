package info.u_team.u_team_core.integration;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.*;
import org.objectweb.asm.Type;

import info.u_team.u_team_core.api.integration.*;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;

public class IntegrationManager {
	
	private static final Logger LOGGER = LogManager.getLogger("IntegrationManager");
	
	public static void constructIntegrations(String modid) {
		final Type type = Type.getType(Integration.class);
		
		final List<AnnotationData> annotations = ModList.get() //
				.getModFileById(modid) //
				.getFile() //
				.getScanResult() //
				.getAnnotations() //
				.stream() //
				.filter(data -> type.equals(data.getAnnotationType())) //
				.collect(Collectors.toList());
		
		for (AnnotationData data : annotations) {
			final String integrationModid = (String) data.getAnnotationData().get("value");
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
