package info.u_team.u_team_core.api;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.*;

/**
 * Models can now be registered directly in items with this interface
 * 
 * @author HyCraftHD
 * @date 24.06.2018
 */
public interface IModelProvider {
	
	@SideOnly(Side.CLIENT)
	public void registerModel();
	
	@SideOnly(Side.CLIENT)
	public default void setModel(Item item, int metadata, ResourceLocation location) {
		ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(location, "inventory"));
	}
	
}
