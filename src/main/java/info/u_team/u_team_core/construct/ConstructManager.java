package info.u_team.u_team_core.construct;

import org.apache.logging.log4j.*;
import org.objectweb.asm.Type;

import info.u_team.u_team_core.api.construct.*;
import info.u_team.u_team_core.util.AnnotationUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData;

public class ConstructManager {
	
	private static final Logger LOGGER = LogManager.getLogger("ConstructManager");
	
	public static void constructConstructs(String modid) {
		for (AnnotationData data : AnnotationUtil.getAnnotations(modid, Type.getType(Construct.class))) {
			final boolean client = (boolean) data.getAnnotationData().get("client");
			if ((client && FMLEnvironment.dist == Dist.CLIENT) || !client) {
				LOGGER.info("Try to load construct for mod " + modid);
				try {
					Class.forName(data.getMemberName()).asSubclass(IModConstruct.class).newInstance().construct();
				} catch (LinkageError | ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException ex) {
					LOGGER.error("Failed to load and construct mod construct : {}", data.getMemberName(), ex);
					throw new RuntimeException(ex);
				}
			}
		}
	}
	
}
