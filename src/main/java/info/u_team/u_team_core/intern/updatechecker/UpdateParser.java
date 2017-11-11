package info.u_team.u_team_core.intern.updatechecker;

import com.google.gson.*;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;

/**
 * Update API<br>
 * -> Update parser
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 *
 */
public class UpdateParser {
	
	private UpdateResult result = null;
	
	private String modid;
	
	public UpdateParser(String modid, String data) {
		this.modid = modid;
		parse(data);
	}
	
	private void parse(String data) {
		JsonElement element = new JsonParser().parse(data);
		JsonElement versionelement = element.getAsJsonObject().get(ForgeVersion.mcVersion);
		if (versionelement == null || versionelement.isJsonNull()) {
			result = new UpdateResult();
		} else {
			check(versionelement.getAsJsonObject().get("version").getAsString(), versionelement.getAsJsonObject().get("download").getAsString());
		}
	}
	
	private void check(String newversion, String download) {
		ModContainer container = Loader.instance().getIndexedModList().get(modid);
		if (container == null) {
			throw new NullPointerException("Modcontainer for modid could not be found.");
		}
		String currentversion = container.getVersion();
		
		DefaultArtifactVersion currentversionartifact = new DefaultArtifactVersion(currentversion);
		DefaultArtifactVersion newversionartifact = new DefaultArtifactVersion(newversion);
		
		if (currentversionartifact.compareTo(newversionartifact) < 0) {
			result = new UpdateResult(newversion, download);
		} else {
			result = new UpdateResult();
		}
	}
	
	public UpdateResult getResult() {
		return result;
	}
	
}
