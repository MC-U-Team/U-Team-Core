package info.u_team.u_team_core.villagerprofession;

import info.u_team.u_team_core.api.registry.IUVillagerProfession;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public class UVillagerProfession extends VillagerProfession implements IUVillagerProfession {
	
	protected final String name;
	
	protected ResourceLocation texturenormal;
	protected ResourceLocation texturezombie;
	
	public UVillagerProfession(String modid, String name) {
		this(modid, name, "textures/entity/villager/" + name + ".png", "textures/entity/villager/" + name + "_zombie.png");
	}
	
	public UVillagerProfession(String modid, String name, String texturepathnormal, String texturepathzombie) {
		this(modid, name, null, texturepathnormal, null, texturepathzombie);
	}
	
	public UVillagerProfession(String modid, String name, String texturedomainnormal, String texturepathnormal, String texturedomainzombie, String texturepathzombie) {
		this(modid, name, new ResourceLocation(texturedomainnormal == null ? modid : texturedomainnormal, texturepathnormal), new ResourceLocation(texturedomainzombie == null ? modid : texturedomainzombie, texturepathzombie));
	}
	
	public UVillagerProfession(String modid, String name, ResourceLocation texturenormal, ResourceLocation texturezombie) {
		super(modid + ":" + name, "", "");
		this.name = name;
		this.texturenormal = texturenormal;
		this.texturezombie = texturenormal;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
	@Override
	public ResourceLocation getSkin() {
		return texturenormal;
	}
	
	@Override
	public ResourceLocation getZombieSkin() {
		return texturezombie;
	}
	
}
