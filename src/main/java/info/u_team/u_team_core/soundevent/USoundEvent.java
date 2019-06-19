package info.u_team.u_team_core.soundevent;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;

public class USoundEvent extends SoundEvent implements IURegistryType {
	
	private static final ResourceLocation INVALID = new ResourceLocation(UCoreMain.MODID, "invalid");
	
	protected final String name;
	
	public USoundEvent(String name) {
		super(INVALID);
		this.name = name;
	}
	
	public USoundEvent(String name, String modid) {
		this(new ResourceLocation(modid, name));
	}
	
	public USoundEvent(ResourceLocation soundName) {
		this(soundName.getPath(), soundName);
	}
	
	public USoundEvent(String name, ResourceLocation soundName) {
		super(soundName);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public ResourceLocation getName() {
		final ResourceLocation resourceLocation = super.getName();
		return INVALID.equals(resourceLocation) ? getRegistryName() : resourceLocation;
	}
	
}
