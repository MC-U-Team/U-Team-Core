package info.u_team.u_team_core.data;

import java.io.IOException;
import java.util.Arrays;

import net.minecraft.resources.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.loading.FMLLoader;

public class ExistingFileHelperWithForge extends ExistingFileHelper {
	
	private final ExistingFileHelper existingFileHelper;
	
	public ExistingFileHelperWithForge(ExistingFileHelper helper) {
		super(Arrays.asList(FMLLoader.getForgePath()), helper.isEnabled());
		this.existingFileHelper = helper;
	}
	
	public boolean exists(ResourceLocation loc, ResourcePackType type, String pathSuffix, String pathPrefix) {
		final boolean exists = existingFileHelper.exists(loc, type, pathSuffix, pathPrefix);
		if (!exists) {
			return super.exists(loc, type, pathSuffix, pathPrefix);
		}
		return exists;
	}
	
	public IResource getResource(ResourceLocation loc, ResourcePackType type, String pathSuffix, String pathPrefix) throws IOException {
		try {
			return existingFileHelper.getResource(loc, type, pathSuffix, pathPrefix);
		} catch (IOException ex) {
			return super.getResource(loc, type, pathSuffix, pathPrefix);
		}
	}
}
